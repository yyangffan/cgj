package com.lhkj.cgj.lock;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ActivitySigncalendarBinding;
import com.lhkj.cgj.databinding.SeePriceBinding;
import com.lhkj.cgj.databinding.SigncalenderItemBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.AdsenseReponse;
import com.lhkj.cgj.network.response.CalenderListReponse;
import com.lhkj.cgj.network.response.CalenderPriceReponse;
import com.lhkj.cgj.network.response.CanlenderDaysReponse;
import com.lhkj.cgj.network.response.HttpResponse;
import com.lhkj.cgj.spirit.calender.CalendarInfo;
import com.lhkj.cgj.ui.mine.DoubtActivity;
import com.lhkj.cgj.ui.mine.SignCalendarActivity;
import com.lhkj.cgj.ui.mine.SignPriceHisActivity;
import com.lhkj.cgj.utils.BitmapUtils;
import com.lhkj.cgj.utils.PopManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/23.
 */

public class SignCalendarLock {
    private Context context;
    private ArrayList<CalendarInfo> signDays;
    private ArrayList<SignCalenderItem> prices;
    private ArrayList<Signs> signs;
    private boolean isCalender;
    private int hasBit;
    private PopManager popManager;

    private ActivitySigncalendarBinding signcalendarBinding;

    public SignCalendarData signCalendarData;
    private String signAlls;

    public SignCalendarLock(Context context, ActivitySigncalendarBinding signcalendarBinding) {
        this.context = context;
        this.signcalendarBinding = signcalendarBinding;
        signCalendarData = new SignCalendarData();
        signs = new ArrayList<>();
        signDays = new ArrayList<>();
        prices = new ArrayList<>();
        signcalendarBinding.signCalendar.setWeekStart(1);
        signcalendarBinding.signCalendar.prpa();
        calendarList();
        noAllDays();
        getAdsen();
    }

    //已签到列表
    private void calendarList() {
        signDays.clear();
        signs.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(CalenderListReponse.class, RunTime.operation.getMine().CALENDER_LIST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (((CalenderListReponse) data).info != null) {
                    for (CalenderListReponse.Info info : ((CalenderListReponse) data).info) {
                        if (info.day == null) {
                            priceList();
                            return;
                        }
                        signs.add(new Signs(Integer.parseInt(info.day.substring(0, 4)), Integer.parseInt(info.day.substring(5, 7)), Integer.parseInt(info.day.substring(8, info.day.length()))));
                    }
                    priceList();
                } else {
                    signcalendarBinding.signCalendar.postInvalidate();
                }

            }
        });
    }

    //签到奖品列表
    private void priceList() {
        signDays.clear();
        prices.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(CalenderPriceReponse.class, RunTime.operation.getMine().CALENDER_PRICE_LIST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                CalenderPriceReponse calenderPriceReponse = (CalenderPriceReponse) data;
                if (calenderPriceReponse.getInfo() != null) {
                    for (CalenderPriceReponse.InfoBean info : calenderPriceReponse.getInfo()) {
                        prices.add(new SignCalenderItem(
                                info.getId(), info.getLink_day(),
                                info.getGoods_id(), info.getGoods_img(),
                                info.getType(), info.getJifen_num()));
                    }
                    addPriceImg();
                }
            }
        });
    }

    private void addPriceImg() {
        signcalendarBinding.calenderPriceList.removeAllViews();
        for (final SignCalenderItem signCalenderItem : prices) {
            //商品奖励
            if (signCalenderItem.goodsIms != null) {
                hasBit++;
                addPriceBit(signCalenderItem);
            } else {
                //积分奖励
                signCalenderItem.goodsBit = BitmapUtils.scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.def_lll_text), 84, 84);

            }
            LayoutInflater ll = LayoutInflater.from(context);
            SigncalenderItemBinding calenderItemBinding = DataBindingUtil.inflate(ll, R.layout.signcalender_item, null, false);
            calenderItemBinding.setCalenderItem(signCalenderItem);
            signcalendarBinding.calenderPriceList.addView(calenderItemBinding.getRoot());
        }
        if (hasBit == 0) {
            isCalender=false;
            initCalender();
        }
    }

    //加载签到日历商品图片
    private void addPriceBit(final SignCalenderItem signCalenderItem) {
        Glide.with(context).asBitmap().load(signCalenderItem.goodsIms).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                signCalenderItem.goodsBit = BitmapUtils.scaleBitmap(resource, 84, 84);
                hasBit--;
                if (hasBit == 0) {
                    isCalender=false;
                    initCalender();
                }
            }
        });
    }

    private void noAllDays() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(CanlenderDaysReponse.class, RunTime.operation.getMine().CALENDER_DAY, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                CanlenderDaysReponse calenderPriceReponse = (CanlenderDaysReponse) data;
                signCalendarData.coDay = "已经连续签到" + calenderPriceReponse.info + "天";
                signAlls = calenderPriceReponse.info + "";
                signCalendarData.notifyChange();

            }
        });
    }

    public void finish() {
        ((SignCalendarActivity) context).finish();
    }

    // 帮助接口
    public void doubt() {
        context.startActivity(new Intent(context, DoubtActivity.class));
    }

    //签到接口
    public void sign() {
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        String mouth = "";
        String days = "";
        if ((1 + date.getMonth()) < 10) {
            mouth = "0" + (1 + date.getMonth());
        } else {
            mouth = "" + (1 + date.getMonth());
        }
        if (date.getDate() < 10) {
            days = "0" + date.getDate();
        } else {
            days = "" + date.getDate();
        }
        hashMap.put("times", (1900 + date.getYear()) + "-" + mouth + "-" + days);
        RunTime.operation.tryPostRefresh(HttpResponse.class, RunTime.operation.getMine().SIGN_CALENDER, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (((HttpResponse) data).getResultcode().equals("200")) {
                    Toast.makeText(context, "签到成功", Toast.LENGTH_SHORT).show();
                    noAllDays();
                    calendarList();
                }
            }
        });
    }


    private void getAdsen() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("cat_id", "2");
        RunTime.operation.tryPostRefresh(AdsenseReponse.class, RunTime.operation.getHome().ADSENSE, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (((AdsenseReponse) data).info != null) {
                    Glide.with(context).load(((AdsenseReponse) data).info.img_url).into(signcalendarBinding.adsen);
                    signCalendarData.isAds = true;
                    signCalendarData.notifyChange();
                }

            }
        });
    }

    private void getCalenderPricesLll(final String goodsId) {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("prize_id", goodsId);
        RunTime.operation.tryPostRefreshF(HttpResponse.class, RunTime.operation.getMine().SIGN_LLL, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                Toast.makeText(context, ((HttpResponse) data).getSuccess(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //添加事务清单
    private void initCalender() {
        if (isCalender) {
            return;
        }
        isCalender = true;
        Date date = new Date(System.currentTimeMillis());
        Signs startDay = new Signs(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
        startDay.days = startDay.days - Integer.parseInt(signAlls);
        for (Signs s : signs) {
            signDays.add(new CalendarInfo(s.years, s.months, s.days, s.days + "", null));
        }
        for (SignCalenderItem item : prices) {
            for (int i = 0; i < signDays.size(); i++) {
                if (signDays.get(i).day == startDay.days + Integer.parseInt(item.days)) {
                    signDays.remove(i);
                }
            }
        }
        for (SignCalenderItem item : prices) {
            signDays.add(new CalendarInfo(startDay.years, startDay.months, startDay.days + Integer.parseInt(item.days), startDay.days + item.days, item.goodsBit));
        }
        signcalendarBinding.signCalendar.setCalendarInfos(signDays);
        signcalendarBinding.signCalendar.postInvalidate();
    }


    public void toSignPriceHis() {
        ((SignCalendarActivity) context).startActivity(SignPriceHisActivity.class);
    }

    public void ads() {

    }

    public void adsClose() {
        signCalendarData.isAds = false;
        signCalendarData.notifyChange();
    }

    public class SignCalendarData extends BaseObservable {
        public boolean isLogin = !User.isLogin();
        public Drawable drawable = User.getUser().userIconClone;
        public String userName = User.getUser().usernike;
        public String coDay = "已经连续签到" + "0天";
        public boolean isAds;
    }

    public class SignCalenderItem {
        public SignCalenderItem(String id, String signDay,
                                String goodsId, String goodsIms,
                                String type, String lll) {
            this.id = id;
            this.signDay = "连续签到" + signDay + "天";
            days = signDay;
            this.goodsId = goodsId;
            this.goodsIms = goodsIms;
            this.type = type;
            if (lll != null && !lll.isEmpty()) {
                this.lll = lll + "积分";
            } else {
                this.lll = "";
            }
        }

        public String id;
        public String signDay;
        public String days;
        public String type;
        public String signPrice;
        public String goodsId;
        public String goodsIms;
        public Bitmap goodsBit;
        public boolean isPush;
        public String lll;

        //领取签到奖品pop
        public void seePrice(final String goodsId) {
            Bitmap b = null;
            SeePriceBinding seePriceBinding;
            String type = null;
            String thisId = null;
            String url = null;
            for (SignCalenderItem item : prices) {
                        if (item.id != null && item.id.equals(goodsId) && item.goodsBit != null) {
                            type = item.type;
                            b = BitmapUtils.scaleBitmap(item.goodsBit, 400, 400);
                            thisId = item.id;
                            url = item.goodsIms;
                }
            }
            popManager = new PopManager(context);
            seePriceBinding = (SeePriceBinding) popManager.showTransparentPop(
                    signcalendarBinding.constraintLayout, R.layout.see_price);
            if (Integer.parseInt(days) > Integer.parseInt(signAlls)) {
                seePriceBinding.setIsPrice(context.getResources().getString(R.string.signnot));
            } else {
                seePriceBinding.setIsPrice(context.getResources().getString(R.string.signgetprice));
                final String type1 = type;
                final String finalThisId = thisId;
                final String finalUrl = url;
                seePriceBinding.priceEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popManager.stop();
                        // 领取礼包
                        getCalenderPricesLll(finalThisId);
                    }
                });
            }
            seePriceBinding.setLll(lll);
            seePriceBinding.priceClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popManager.stop();
                }
            });
            seePriceBinding.priceImg.setImageBitmap(b);
        }
    }

    public class Signs {
        public Signs(int years, int months, int days) {
            this.years = years;
            this.months = months;
            this.days = days;
        }

        public int years;
        public int months;
        public int days;

    }
}
