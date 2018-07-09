package com.lhkj.cgj.lock;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ActivityShopDetailsBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.NowLllReponse;
import com.lhkj.cgj.network.response.ShopDetailsResponse;
import com.lhkj.cgj.network.response.ShopOrderReponse;
import com.lhkj.cgj.utils.NetworkImageHolderView;
import com.lhkj.cgj.utils.PayUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/21.
 * 商品详情
 */

public class ShopDetailsLock {
    public ShopDetailsData shopDetailsData;
    private Context context;
    private ActivityShopDetailsBinding shopDetailsBinding;
    private Handler handler;
    private ArrayList networkImages;
    private String orderId;
    private double _money;
    private double _price;
    private String _l;
    private String _m = "0";
    private String _type = "0";
    private String payTpye = "0";

    public ShopDetailsLock(Context context, final ActivityShopDetailsBinding shopDetailsBinding) {
        this.context = context;
        this.shopDetailsBinding = shopDetailsBinding;
        shopDetailsData = new ShopDetailsData();
        shopDetailsBinding.characteristicIms.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        shopDetailsBinding.characteristicIms.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        shopDetailsBinding.characteristicIms.getSettings().setJavaScriptEnabled(true);
        shopDetailsBinding.characteristicIms.getSettings().setDomStorageEnabled(true);
        shopDetailsBinding.characteristicIms.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = context.getApplicationContext().getCacheDir().getAbsolutePath();
        shopDetailsBinding.characteristicIms.getSettings().setAppCachePath(appCachePath);
        shopDetailsBinding.characteristicIms.getSettings().setAppCacheEnabled(true);
        shopDetailsBinding.characteristicIms.getSettings().setAllowFileAccess(true);


        shopDetailsBinding.characteristicIms.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);



        shopDetailsBinding.characteristicIms.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                shopDetailsBinding.scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        shopDetailsBinding.orPay.setChecked(true);
        //初始化详情页
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("goods_id", RunTime.getRunTime(RunTime.SHOPID) + "");
        RunTime.operation.tryPostRefresh(ShopDetailsResponse.class, RunTime.operation.getShop().SHOP_DEAT, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                ShopDetailsResponse shopDetailsResponse = (ShopDetailsResponse) data;
                shopDetailsData.detailsName = shopDetailsResponse.info.goods_name;
                switch (shopDetailsResponse.info.shop_type) {
                    case "1":  // 现金
                        shopDetailsData.isType = true;
                        shopDetailsData.isTwo = false;
                        shopDetailsData.detailsNote = "直接购买";
                        break;
                    case "2":  // 积分
                        shopDetailsData.isType = false;
                        shopDetailsData.isTwo = false;
                        shopDetailsData.detailsNote = "直接积分兑换";
                        shopDetailsData.payEnter = "积分兑换";
                        break;
                    case "3":  // 现金加积分
                        shopDetailsData.isType = true;
                        shopDetailsData.isTwo = true;
                        if (shopDetailsResponse.info.jifen != null && Double.parseDouble(shopDetailsResponse.info.jifen) != 0) {
                            _money = Double.parseDouble(shopDetailsResponse.info.exchange_integral) / Double.parseDouble(shopDetailsResponse.info.jifen);
                            java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
                            _m = df.format(_money);
                        }
                        shopDetailsData.detailsNote = shopDetailsResponse.info.exchange_integral + "积分抵" + _m + "元";
                        break;
                }
                _l = shopDetailsResponse.info.exchange_integral;
                _type = shopDetailsResponse.info.shop_type;
                if(!"1".equals(shopDetailsResponse.info.is_hot)){
                    shopDetailsBinding.isHot.setVisibility(View.GONE);
                }
                shopDetailsData.stock = shopDetailsResponse.info.store_count;
                if (_type.equals("3")){
                    payTpye = "1";
                }else {
                    payTpye = _type;
                }
                _price = Double.parseDouble(shopDetailsResponse.info.shop_price);
                if (shopDetailsData.isType) {
                    shopDetailsData.payOr = "￥" + shopDetailsResponse.info.shop_price;
                } else {
                    shopDetailsData.payOr = shopDetailsResponse.info.exchange_integral;
                }
                if (shopDetailsResponse.info.is_hot != null && shopDetailsResponse.info.is_hot.equals("1")) {
                    shopDetailsData.isHot = true;
                }
                shopDetailsData.notifyChange();
                networkImages = new ArrayList();
                for (String url : shopDetailsResponse.info.img) {
                    networkImages.add(url);
                }

                String content=shopDetailsResponse.info.goods_content.replace("<img", "<img height=\"auto\"; width=\"100%\"");
                shopDetailsBinding.characteristicIms.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
//                shopDetailsBinding.characteristicIms.loadDataWithBaseURL(null, shopDetailsResponse.info.goods_content, "text/html", "utf-8", null);
                shopDetailsBinding.shopDetailsIms.startTurning(2500);
                shopDetailsBinding.shopDetailsIms.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, networkImages);
                generatePay();
            }
        });
        //从我的订单过来支付的商品
        if (((int) RunTime.getRunTime(RunTime.SHOP_TYPE)) == 1) {
            getMyLll();
            shopDetailsData.orderSn = "订单号  " + RunTime.getRunTime(RunTime.ORDER_NUM);
            orderId = "" + RunTime.getRunTime(RunTime.ORDER_ID);
            ex();
        }
    }

    public void toPay() {
        if (shopDetailsBinding.showType1.getVisibility() == View.VISIBLE) {
            nowPay();
        } else {
            toPrpaPay();
            getMyLll();
        }

    }

    public void orPay() {
        if (_type.equals("3")) {
            payTpye = "1";
            shopDetailsData.payEnter = "直接购买(￥" + _price + ")";
        } else if (_type.equals("2")) {
            payTpye = "2";
            shopDetailsData.payEnter = "直接兑换(" + _l + "积分)";
        }
        shopDetailsData.notifyChange();
    }

    public void andPay() {
        payTpye = "3";
        shopDetailsData.payEnter = "支付现金(￥" + (_price - Double.parseDouble(_m)) + ")兑换";
        shopDetailsData.notifyChange();
    }

    private void getMyLll() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(NowLllReponse.class, RunTime.operation.getShop().MY_LLL, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                shopDetailsData.myLll = "我的积分:" + ((NowLllReponse) data).info;
                shopDetailsData.notifyChange();
            }
        });
    }

    //初始化支付页
    private void toPrpaPay() {
        HashMap hashMap = new HashMap();
        hashMap.put("goods_id", RunTime.getRunTime(RunTime.SHOPID) + "");
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(ShopOrderReponse.class, RunTime.operation.getShop().CREATE_ORDER, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                ShopOrderReponse shopDetailsResponse = (ShopOrderReponse) data;
                if (shopDetailsResponse.info.exchang_integral != null) {
                    _money = Double.parseDouble(shopDetailsResponse.info.exchang_integral) / Double.parseDouble(shopDetailsResponse.info.jifen);
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
                    _m = df.format(_money);
                }
                shopDetailsData.orderSn = "订单号  " + shopDetailsResponse.info.order_sn;
                orderId = shopDetailsResponse.info.order_id;
                _price = Double.parseDouble(shopDetailsResponse.info.shop_price);
                generatePay();
                ex();
            }
        });
    }

    private void generatePay() {
        switch (_type) {
            case "1":
                shopDetailsData.payEnter = "直接购买(￥" + _price + ")";
                shopDetailsData.payGet = "直接购买(￥" + _price + ")";
                break;
            case "2":
                shopDetailsData.payEnter = "直接兑换(" + _l + "积分)";
                shopDetailsData.payGet = "积分兑换(" + _l + "积分)";
                break;
            case "3":
                shopDetailsData.payEnter = "直接购买(￥" + _price + ")";
                shopDetailsData.lllGet = "积分兑换(" + _l + "积分可以兑换￥" + _m + ")";
                shopDetailsData.payGet = "直接购买(￥" + _price + ")";
                break;
        }
        shopDetailsData.notifyChange();

    }

    //转换显示
    private void ex() {
        shopDetailsBinding.myIntegral.setVisibility(View.VISIBLE);
        shopDetailsBinding.showType1.setVisibility(View.VISIBLE);
        shopDetailsBinding.showType.setVisibility(View.GONE);
    }


    private void nowPay() {
        shopPay();
    }

    private void shopPay() {
        //去支付
        PayUtils payUtils = new PayUtils();
        payUtils.tryPay(context, orderId, payTpye);
    }

    public class ShopDetailsData extends BaseObservable {

        ShopDetailsData() {
            payEnter = context.getResources().getString(R.string.nowpay);
        }

        public boolean isType;
        public boolean isTwo;
        public boolean isHot;
        public String detailsName;
        public String payOr;
        public String detailsNote;
        public String myLll;
        public String orderSn;
        public String lllGet;
        public String payGet;
        public String payEnter;
        public String stock;
    }
}
