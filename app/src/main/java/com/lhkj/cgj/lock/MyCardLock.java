package com.lhkj.cgj.lock;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ActivityMycardBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.BannerResponse;
import com.lhkj.cgj.network.response.CarZhuanZengResponse;
import com.lhkj.cgj.network.response.CouponListResponse;
import com.lhkj.cgj.network.response.CouponResponse;
import com.lhkj.cgj.ui.main.ShopTypeAdapter;
import com.lhkj.cgj.ui.mine.CardListAdapter;
import com.lhkj.cgj.ui.mine.MyCardActivity;
import com.lhkj.cgj.ui.other.MyQrCodeActivity;
import com.lhkj.cgj.utils.NetworkImageHolderView;
import com.lhkj.cgj.wxapi.sharesdk.onekeyshare.OnekeyShare;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/22.
 * 优惠券
 */

public class MyCardLock  {
    private TextView selectView;
    private ArrayList<CardItem> cardListData;
    private Context context;
    public CardListAdapter cardAdapter;
    private ArrayList<CardCatItem> cardType;
    private ArrayList<String> cardTypeNames;
    private ActivityMycardBinding mycardBinding;
    private ShopTypeAdapter cardTypeAdapter;
    private ArrayList networkImages;
    private int position = 0;

    public MyCardLock(Context context, ActivityMycardBinding mycardBinding) {
        this.context = context;
        cardListData = new ArrayList<>();
        networkImages = new ArrayList();
        this.mycardBinding = mycardBinding;
        cardTypeNames = new ArrayList<>();
        cardAdapter = new CardListAdapter(context, cardListData, R.layout.card_item, BR.cardItem);
        cardType = new ArrayList<>();
        getData();
        couponListCat();
    }

    private void getData() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(BannerResponse.class, RunTime.operation.getMine().COUPON_BANNER, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (((BannerResponse) data).info != null) {
                    for (BannerResponse.Info info : ((BannerResponse) data).info) {
                        networkImages.add(info.img_url);
                    }
                    mycardBinding.cardIms.startTurning(2500);
                    mycardBinding.cardIms.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                        @Override
                        public NetworkImageHolderView createHolder() {
                            return new NetworkImageHolderView();
                        }
                    }, networkImages);
//                .setPageIndicator(new int[]{com.bigkoo.convenientbanner.R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
                }

            }
        });

    }

    //优惠券类型
    public void couponListCat() {
        cardType.clear();
        cardTypeNames.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(CouponResponse.class, RunTime.operation.getMine().COUPON_LIST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                CouponResponse couponResponse = (CouponResponse) data;
                for (CouponResponse.Info info : couponResponse.info) {
                    cardType.add(new CardCatItem(info.cat_id, info.cate_name));
                    cardTypeNames.add(info.cate_name);
                }
                if (cardType.size() != 0) {
                    initLayout();
                }

            }
        });
    }

    private void initLayout() {
        cardTypeAdapter = new ShopTypeAdapter(context, cardTypeNames);
        LinearLayoutManager ms = new LinearLayoutManager(context);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        mycardBinding.cardCatList.setLayoutManager(ms);
        mycardBinding.cardCatList.setAdapter(cardTypeAdapter);
        cardTypeAdapter.setItemClickListener(new ShopTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectView((TextView) view);
                couponList(position);
                MyCardLock.this.position = position;
            }
        });
        couponList(0);
    }

    //    加载优惠券列表
    public void refreshList() {
//    public void refreshList(int pos){
//        if(pos!=-1) {
//            couponList(pos);
//        }else {
        couponList(position);
//        }
    }

    //    加载优惠券列表
    private void couponList(int position) {
        cardListData.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("cat_id", cardType.get(position).catId);
        RunTime.operation.tryPostRefreshF(CouponListResponse.class, RunTime.operation.getMine().COUPON_POST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (id == 200) {
                    mycardBinding.couponNull.setVisibility(View.GONE);
                    CouponListResponse couponListResponse = (CouponListResponse) data;
                    for (CouponListResponse.Info info : couponListResponse.info) {
                        Date date = new Date();
                        date.setTime(Long.parseLong(info.use_end_time + "000"));
                        info.use_end_time = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + (date.getDate());
                        cardListData.add(new CardItem(info.coupon_id, info.name, info.use_end_time, info.money, info.state, info.coupon_id, info.state_id, info.exps));
                    }
                    refresh();
                } else {
                    mycardBinding.couponNull.setVisibility(View.VISIBLE);
                }
                if (selectView == null) {
                    selectView(cardTypeAdapter.getOneText());
                }

            }
        });
        //使用优惠券
        cardAdapter.setOnItemClickListener(new CardListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                switch (cardListData.get(position).state) {
                    case "0":
                        RunTime.setData(RunTime.COUPON_ID, cardListData.get(position).couponId);
                        RunTime.setData(RunTime.STATE_ID, cardListData.get(position).stateId);
                        RunTime.setData(RunTime.CODE_TYPE, MyQrCodeActivity.CODE_COUPON);
                        ((MyCardActivity) context).startActivity(MyQrCodeActivity.class);
                        break;
                    case "1":
                        Toast.makeText(context, "优惠券已使用", Toast.LENGTH_SHORT).show();
                        break;
                    case "2":
                        Toast.makeText(context, "优惠券已过期", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        //长按优惠券进行分享
        cardAdapter.setOnItemClickLongListener(new CardListAdapter.OnItemClickLongListener() {
            @Override
            public void OnItemClickLongListener(int position) {
                checkYhqState(cardListData.get(position).stateId);
            }
        });
//        mycardBinding.cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (cardListData.get(position).state) {
//                    case "0":
//                        RunTime.setData(RunTime.COUPON_ID, cardListData.get(position).couponId);
//                        RunTime.setData(RunTime.STATE_ID, cardListData.get(position).stateId);
//                        RunTime.setData(RunTime.CODE_TYPE, MyQrCodeActivity.CODE_COUPON);
//                        ((MyCardActivity) context).startActivity(MyQrCodeActivity.class);
//                        break;
//                    case "1":
//                        Toast.makeText(context, "优惠券已使用", Toast.LENGTH_SHORT).show();
//                        break;
//                    case "2":
//                        Toast.makeText(context, "优惠券已过期", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });

    }

    /*检查优惠券是否可赠送并且获取赠送链接*/
    public void checkYhqState(String state_id) {
        HashMap hashMap = new HashMap();
        hashMap.put("state_id", state_id);
        RunTime.operation.tryPostRefresh(CarZhuanZengResponse.class, RunTime.operation.getMine().COPZHUANZENG, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                CarZhuanZengResponse response = (CarZhuanZengResponse) data;
                if (response.getResultcode().equals("200")) {
                    showShare(response.getUrl(), "优惠券转赠");
                } else {
                    Toast.makeText(context, response.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showShare(String url, String title) {
        String shareUrl = "";
        shareUrl = url;
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setSilent(true);//隐藏编辑页面
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
//        oks.setTitle(context.getResources().getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(shareUrl);  // 分享的网站名
        // text是分享文本，所有平台都需要这个字段
        oks.setText("你的朋友送你的优惠券,请尽快使用哦");  // 分享的文字
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/yph/logo.png");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("你的朋友送你的优惠券,请尽快使用哦");
//        oks.setComment("我发现一个好玩的应用——油品惠");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getResources().getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareUrl);
        // 启动分享GUI
        oks.show(context);
    }

    //
    private void selectView(TextView textView) {
        if (selectView == null) {
            selectView = textView;
        }
        selectView.setTextColor(context.getResources().getColor(R.color.gray));
        selectView.setBackgroundColor(context.getResources().getColor(R.color.white));
        textView.setTextColor(context.getResources().getColor(R.color.main_color));
        textView.setBackground(context.getResources().getDrawable(R.drawable.select_red));
        selectView = textView;
    }

    private void refresh() {
        cardAdapter.notifyDataSetChanged();
    }

    public class CardCatItem {
        public CardCatItem(String catId, String catName) {
            this.catId = catId;
            this.catName = catName;
        }

        public String catId;
        public String catName;

    }

    public class CardItem {
        public CardItem(String cardId, String cardName, String cardTime, String cardPay, String state, String couponId, String stateId, String exps) {
            this.cardId = cardId;
            this.cardName = cardName;
            this.cardTime = cardTime;
            this.cardPay = cardPay;
            this.state = state;
            this.couponId = couponId;
            this.stateId = stateId;
            this.exps = exps;
        }

        public String cardId;
        public String cardName;
        public String cardTime;
        public String cardPay;
        public String state;
        public String couponId;
        public String stateId;
        public String exps;
    }
}
