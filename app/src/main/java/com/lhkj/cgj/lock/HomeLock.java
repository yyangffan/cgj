package com.lhkj.cgj.lock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.FragmentHomeBinding;
import com.lhkj.cgj.entity.News;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.BannerResponse;
import com.lhkj.cgj.network.response.CatListResponse;
import com.lhkj.cgj.network.response.NewsListResponse;
import com.lhkj.cgj.network.response.RedbaoResponse;
import com.lhkj.cgj.network.response.TodayOilNewResponse;
import com.lhkj.cgj.network.response.ZixunResponse;
import com.lhkj.cgj.ui.bbs.ActiveListActivity;
import com.lhkj.cgj.ui.bbs.NewsActivity;
import com.lhkj.cgj.ui.bbs.NewsDetailsActivity;
import com.lhkj.cgj.ui.login.LoginActivity;
import com.lhkj.cgj.ui.main.HomeNewsAdapter;
import com.lhkj.cgj.ui.main.HomeOilAdapter;
import com.lhkj.cgj.ui.main.MainActivity;
import com.lhkj.cgj.ui.main.ZixunDetailActivity;
import com.lhkj.cgj.ui.mine.MyCardActivity;
import com.lhkj.cgj.ui.mine.RedBaoAdapter;
import com.lhkj.cgj.ui.other.MyQrCodeActivity;
import com.lhkj.cgj.ui.other.ViolationInquiryActivity;
import com.lhkj.cgj.ui.other.vertical_view.MarqueeView;
import com.lhkj.cgj.ui.station.BindingActivity;
import com.lhkj.cgj.utils.NetworkImageHolderView;
import com.lhkj.cgj.utils.SharedPreferencesUtil;
import com.tencent.mm.opensdk.utils.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 浩琦 on 2017/6/19.
 * 首页
 */

public class HomeLock {
    public BaseTopAdapter homeAdapter;
    public HomeNewsAdapter homeNewsAdapter;
    public HomeData homeData;
    private ArrayList<HomeItem0> listData0;
    private ArrayList<HomeItem1> listData1;
    private Context context;
    private FragmentHomeBinding homeBinding;
    private ArrayList<String> networkImages;
    private ArrayList<CatListResponse.Info> catList;
    private Handler handler = new Handler();
    private ArrayList<HomeOilItem> oils;
    private HomeOilAdapter homeOilAdapter;

    BaseTopAdapter cardAdapter;
    private List<CardItem> cardListData;

    private List<String> mStrings;
    private List<String> mStrings_zixun;
    private List<String> mInfoBeen;


    public HomeLock(final Context context, FragmentHomeBinding homeBinding) {
        this.context = context;
        this.homeBinding = homeBinding;
        homeData = new HomeData();
        mStrings = new ArrayList<>();
        mStrings_zixun = new ArrayList<>();
        catList = new ArrayList<>();
        oils = new ArrayList<>();
        cardListData = new ArrayList<>();
        mInfoBeen = new ArrayList<>();
        homeOilAdapter = new HomeOilAdapter(context, oils);
        listData1 = new ArrayList<>();
        homeNewsAdapter = new HomeNewsAdapter(context, listData1, R.layout.home_item_1, BR.homeItem1);
        init();
//        /*如下添加更新*/
//        UpdateManager updateManager = new UpdateManager(context, false);
//        updateManager.checkUpdate();
//        updateManager.setOnCancelClickListener(new UpdateManager.OnCancelClickListener() {
//            @Override
//            public void OnCancelClickListener() {
//                ((MainActivity) context).finish();
//            }
//        });
    }

    private void init() {
        listData0 = new ArrayList<>();
        listData0.add(new HomeItem0(context.getResources().getDrawable(R.mipmap.cheliangbaoyang), context.getResources().getString(R.string.maintain)));
        listData0.add(new HomeItem0(context.getResources().getDrawable(R.mipmap.qichemeirong), context.getResources().getString(R.string.cosmetology)));
        listData0.add(new HomeItem0(context.getResources().getDrawable(R.mipmap.chexianbanli), context.getResources().getString(R.string.insurance)));
        listData0.add(new HomeItem0(context.getResources().getDrawable(R.mipmap.daijia), context.getResources().getString(R.string.drive)));
        listData0.add(new HomeItem0(context.getResources().getDrawable(R.mipmap.daolujiuyuan), context.getResources().getString(R.string.rescue)));
        listData0.add(new HomeItem0(context.getResources().getDrawable(R.mipmap.weizhangchaxun), context.getResources().getString(R.string.lllegal)));
        listData0.add(new HomeItem0(context.getResources().getDrawable(R.mipmap.huodong), context.getResources().getString(R.string.discount_active)));
        listData0.add(new HomeItem0(context.getResources().getDrawable(R.mipmap.erweima), context.getResources().getString(R.string.qrcode)));
        homeAdapter = new BaseTopAdapter(context, listData0, R.layout.home_item_0, BR.homeItem0);
        homeBinding.homeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                to(position);
            }
        });
        networkImages = new ArrayList<>();
        tryLogin();
        handler.post(new Runnable() {
            @Override
            public void run() {
                homeBinding.scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    private void tryLogin() {
        if (!SharedPreferencesUtil.getSharePreString(context, User.getUser().SAVE_NAME, "").equals("")) {
            RunTime.operation.getMine().tryLogin(
                    SharedPreferencesUtil.getSharePreString(context, User.getUser().SAVE_NAME, ""),
                    SharedPreferencesUtil.getSharePreString(context, User.getUser().SAVE_PWD, ""),
                    new User.AuthorizationListener() {
                        @Override
                        public void authorization(boolean isOk) {
                            if (isOk) {
                                initUrl();
                                getZixun();
                                ((MainActivity) context).flushBar();
                            } else {
                                initUrl();
                                getZixun();
                            }
                        }
                    });
        } else {
            initUrl();
            getZixun();
        }
    }

    /*获取资讯显示内容*/
    public void getZixun() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(ZixunResponse.class, RunTime.operation.getHome().ZIXUN, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                ZixunResponse response = (ZixunResponse) data;
                if (response.getResultcode().equals("200")) {
                    mStrings_zixun.clear();
                    for (int i = 0; i < response.getInfo().size(); i++) {
                        mStrings_zixun.add(response.getInfo().get(i).getZx_title());
                        mInfoBeen.add(response.getInfo().get(i).getId());
                    }
                    homeBinding.marqueeViewtwo.startWithList(mStrings_zixun, R.anim.anim_bottom_in, R.anim.anim_top_out);
                } else {
                    mStrings_zixun.clear();
                    for (int i = 0; i < 2; i++) {
                        mStrings_zixun.add("暂无活动");
                    }
                    mInfoBeen.clear();
                    homeBinding.marqueeViewtwo.startWithList(mStrings_zixun, R.anim.anim_bottom_in, R.anim.anim_top_out);
                }

            }
        });
        homeBinding.marqueeViewtwo.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                if (mInfoBeen.size() != 0) {
                    Intent intent = new Intent(context, ZixunDetailActivity.class);
                    intent.putExtra("zx_id", mInfoBeen.get(position));
                    context.startActivity(intent);
                }
            }
        });
    }

    private void initUrl() {
        initDialog();

        LinearLayoutManager ms = new LinearLayoutManager(context);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeBinding.homeOilList.setLayoutManager(ms);
        homeBinding.homeOilList.setAdapter(homeOilAdapter);
        oils.clear();
        //oil
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        Log.e("打印user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(TodayOilNewResponse.class, RunTime.operation.getHome().OIL_PAY, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                TodayOilNewResponse result = (TodayOilNewResponse) data;
//                for (int i = 0; i < result.getInfoX().size(); i++) {
//                    oils.add(new HomeOilItem(result.getInfoX().get(i).getYou_num(), result.getInfoX().get(i).getPrice()));
//                }
                if (User.isLogin()) {
                    if (mStrings.size() == 0 || mStrings.get(0).equals("号汽油0元")) {
                        mStrings.clear();
                        if (result.getInfo().size() == 0) {/*如果没有绑定油站的话就不会有油价*/
                            for (int i = 0; i < 2; i++) {
                                mStrings.add("号汽油" + "0元");
                            }
                        } else {
                            for (int i = 0; i < result.getInfo().size(); i++) {
                                mStrings.add(result.getInfo().get(i));
                            }
                        }
                        homeBinding.marqueeView.startWithList(mStrings, R.anim.anim_bottom_in, R.anim.anim_top_out);
                    }
                } else {
                    mStrings.clear();
                    for (int i = 0; i < 2; i++) {
                        mStrings.add("号汽油" + "0元");
                    }
                    homeBinding.marqueeView.startWithList(mStrings, R.anim.anim_bottom_in, R.anim.anim_top_out);
                }
                for (String s : mStrings) {
                    Log.e("打印加油数据:", s + "\n");
                }

                homeOilAdapter.notifyDataSetChanged();
            }
        });
        networkImages = new ArrayList<>();
        HashMap hashMap1 = new HashMap();
        hashMap1.put("user_id", User.getUser().userId);
        //boo
        RunTime.operation.tryPostRefresh(BannerResponse.class, RunTime.operation.getHome().BOO_BITMAPS, hashMap1, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (((BannerResponse) data).info != null) {
                    for (BannerResponse.Info info : ((BannerResponse) data).info) {
                        networkImages.add(info.img_url);
                    }
                }
                homeBinding.homeIms.startTurning(2500);
                homeBinding.homeIms.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, networkImages);
                homeBinding.homeIms.notifyDataSetChanged();
//                .setPageIndicator(new int[]{com.bigkoo.convenientbanner.R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
            }
        });
        catList.clear();
        RunTime.operation.tryPostRefresh(CatListResponse.class, RunTime.operation.getHome().CATLIST, new HashMap(), new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                catList.addAll(((CatListResponse) data).info);
            }
        });
        paprNews();
    }

    private void paprNews() {
        homeBinding.homeNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news;
                if (listData1.get(position).imsTitle.length() > 10) {
                    news = new News(listData1.get(position).imsId, listData1.get(position).imsUrl,
                            listData1.get(position).imsTitle,
                            listData1.get(position).imsNote, true, listData1.get(position).zan);
                } else {
                    news = new News(listData1.get(position).imsId, listData1.get(position).imsUrl,
                            listData1.get(position).imsTitle, listData1.get(position).imsNote,
                            true, listData1.get(position).zan);
                }
                RunTime.setData(RunTime.NEWID, news);
                ((MainActivity) context).startActivity(NewsDetailsActivity.class);
            }
        });
        //news
        HashMap itm1 = new HashMap();
        if (User.getUser().userId != null) {
            itm1.put("user_id", User.getUser().userId);
        }
        listData1.clear();
        homeBinding.homeNews.setVisibility(View.GONE);
        RunTime.operation.tryPostRefresh(NewsListResponse.class, RunTime.operation.getHome().HOME_NEWS, itm1, new Operation.Listener() {
            @Override
            public void tryReturn(int id, final Object data) {
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NewsListResponse list = (NewsListResponse) data;
                        if (list.info != null) {
                            for (NewsListResponse.Info info : list.info) {
//                        if (info.title.length() > 12) {
//                            listData1.add(new HomeItem1(info.article_id, info.img_url, info.zhaiyao, info.title.substring(0, 11) + "...", info.content, info.add_time, info.click, info.is_click));
//                        } else {
                                listData1.add(new HomeItem1(info.article_id, info.img_url, info.zhaiyao, info.title, info.content, info.add_time, info.click, info.is_click));
//                        }
                            }
                        }
                        homeNewsAdapter.notifyDataSetChanged();
                        homeBinding.homeNews.setVisibility(View.VISIBLE);

                    }
                });
            }
        });

    }

    //展示红包dialog
    private Dialog initDialog() {
        cardAdapter = new RedBaoAdapter(context, cardListData, R.layout.dialog_lvitem, BR.cardItem);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.redbao_dialog, null);
        ListView lv = (ListView) v.findViewById(R.id.redbao_lv);
        ImageView imageView = (ImageView) v.findViewById(R.id.redbao_imgv_cancle);
        builder.setView(v);
        builder.setCancelable(false);
        lv.setAdapter(cardAdapter);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(RedbaoResponse.class, RunTime.operation.getMine().REDBAO, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                RedbaoResponse rr = (RedbaoResponse) data;
                if (id == 200) {
                    if (rr.info != null) {
                        cardListData.clear();
                        for (RedbaoResponse.Info r : rr.info) {
                            Log.e("红包数据", r.toString());
                            Date date = new Date();
                            date.setTime(Long.parseLong(r.use_end_time + "000"));
                            r.use_end_time = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + (date.getDate());
                            cardListData.add(new CardItem(r.red_id, r.red_name, r.coupon_name, r.coupon_id, r.money, r.xuman_price, r.use_end_time, r.name));
                        }
                        cardAdapter.notifyDataSetChanged();
                    }
                    dialog.show();
                } else {
                    dialog.dismiss();
                }

            }
        });
        if (cardListData.size() < 3) {
            ViewGroup.LayoutParams layoutParams = lv.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lv.setLayoutParams(layoutParams);
        }
        /*假数据下*/
//          for (int i=0;i<2;i++) {
//                  cardListData.add(new CardItem(""+i,"哈哈"+i,"名字"+i,"id"+i,"50"+i,"11"+i,"2017-5-15"+i));
//          }
//         cardAdapter.notifyDataSetChanged();
//        dialog.show();
    /*假数据上*/


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dialog.isShowing()) {
                    context.startActivity(new MyCardActivity().getCallIntent(context, 2));
                    dialog.dismiss();
                }
            }
        });
        return dialog;
    }


    // 首页跳到各页面
    private void to(int item) {
        if (catList.size() == 0) {
            initUrl();
            return;
        }
        switch (item) {
            case 0:
                RunTime.setData(RunTime.NEWSTYPE, NewsActivity.MAINTAIN);
                RunTime.setData(RunTime.CAT_ID, catList.get(item).cat_id);
                ((MainActivity) context).startActivity(NewsActivity.class);
                break;
            case 1:
                RunTime.setData(RunTime.NEWSTYPE, NewsActivity.COSMETOLOGY);
                RunTime.setData(RunTime.CAT_ID, catList.get(item).cat_id);
                ((MainActivity) context).startActivity(NewsActivity.class);
                break;
            case 2:
                RunTime.setData(RunTime.NEWSTYPE, NewsActivity.INSURANCE);
                RunTime.setData(RunTime.CAT_ID, catList.get(item).cat_id);
                ((MainActivity) context).startActivity(NewsActivity.class);
                break;
            case 3:
                RunTime.setData(RunTime.NEWSTYPE, NewsActivity.DRIVE);
                RunTime.setData(RunTime.CAT_ID, catList.get(item).cat_id);
                ((MainActivity) context).startActivity(NewsActivity.class);
                break;
            case 4:
                RunTime.setData(RunTime.NEWSTYPE, NewsActivity.RESCUE);
                RunTime.setData(RunTime.CAT_ID, catList.get(item).cat_id);
                ((MainActivity) context).startActivity(NewsActivity.class);
                break;
            case 5:
//                RunTime.setData(RunTime.NEWSTYPE, NewsActivity.LLLEGAL);
//                RunTime.setData(RunTime.CAT_ID, catList.get(item).cat_id);
//                ((MainActivity) context).startActivity(NewsActivity.class);
                // 違章查詢
                ((MainActivity) context).startActivity(ViolationInquiryActivity.class);
                break;
            case 6:
//                RunTime.setData(RunTime.NEWSTYPE, NewsActivity.ACTIVE);
//                RunTime.setData(RunTime.CAT_ID, catList.get(item).cat_id);
//                ((MainActivity) context).startActivity(NewsActivity.class);
                if (!User.isLogin()) {
                    ((MainActivity) context).startActivity(LoginActivity.class);
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO: 2018/4/13 修改成跳转到ActiveListActivity中因为接口完全变了
                ((MainActivity) context).startActivity(ActiveListActivity.class);
                break;
            case 7:
                if (!User.isLogin()) {
                    ((MainActivity) context).startActivity(LoginActivity.class);
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (User.getUser().userOilId == null || User.getUser().userOilId.equals("")) {
                    ((MainActivity) context).startActivity(BindingActivity.class);
                    Toast.makeText(context, "请先绑定加油站", Toast.LENGTH_SHORT).show();
                    return;
                }
                RunTime.setData(RunTime.CODE_TYPE, 0);
                ((MainActivity) context).startActivity(MyQrCodeActivity.class);

                break;
        }
    }

    public void flush() {
        initUrl();
        getZixun();
    }

    public class CardItem {
        public CardItem(String redid, String redname, String couponname, String couponid, String money, String xumanprice, String useendtime, String name) {
            this.redid = redid;
            this.redname = redname;
            this.couponname = couponname;
            this.couponid = couponid;
            this.money = money;
            this.xumanprice = xumanprice;
            this.useendtime = useendtime;
            this.name = name;
        }

        public String redid;
        public String redname;
        public String couponname;
        public String couponid;
        public String money;
        public String xumanprice;
        public String useendtime;
        public String name;
    }

    public class HomeItem0 {
        public HomeItem0(Drawable ims, String imsNote) {
            this.ims = ims;
            this.imsNote = imsNote;
        }

        public Drawable ims;
        public String imsNote;
    }

    public class HomeItem1 {
        public HomeItem1(String imsId, String imsUrl, String imsText, String imsTitle, String imsNote, String imsTime, String zan, String isZan) {
            this.imsId = imsId;
            this.imsText = imsText;
            this.imsTitle = imsTitle;
            this.imsNote = imsNote;
            this.imsTime = imsTime;
            this.imsUrl = imsUrl;
            this.zan = zan;
            this.isZan = isZan;
        }

        public String zan;
        public String isZan;
        public String imsText;
        public String imsId;
        public String imsTitle;
        public String imsTime;
        public String imsUrl;
        public String imsNote;
    }

    public class HomeOilItem {
        public HomeOilItem(String oilName, String oilPrice) {
            this.oilName = oilName;
            this.oilPrice = oilPrice;
        }

        public String oilName;
        public String oilPrice;

    }

    public class HomeData extends BaseObservable {
        public String price0;
        public String price92;
        public String price93;
        public String price99;

        public void transformationData(String pay) {
            if (price92 == null) {
                price92 = pay;
            } else if (price93 == null) {
                price93 = pay;
            } else if (price99 == null) {
                price99 = pay;
            }
        }
    }
}
