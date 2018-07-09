package com.lhkj.cgj.entity;

/**
 * Created by 浩琦 on 2017/6/21.
 */

public class Shop {
    public final String DEFDEATAILSTYPE = "";
    public final String IMSDEATAILSTYPE = "";
    public final String CAT_LIST = RunTime.BASEURL + "Mobile/Shop/cat";
    public final String LIFE_LIST = RunTime.BASEURL + "Mobile/Shop/goods_list/";
    public final String EXCHANGE = RunTime.BASEURL + "Mobile/Order/exchange";
    public final String HOT_LIST = RunTime.BASEURL + "Mobile/Shop/hot";
    public final String SHOP_DEAT = RunTime.BASEURL + "Mobile/Shop/goods_xq";
    public final String CREATE_ORDER = RunTime.BASEURL + "Mobile/Order/buy";
    public final String SHOP_BANNER=RunTime.BASEURL+"Mobile/User/get_shop_banner";
    public final String MY_LLL=RunTime.BASEURL+"Mobile/User/my_jifen";
    public final String GO_PAY=RunTime.BASEURL+"Mobile/Order/go_pay";
    public final String MY_EXCHANGE=RunTime.BASEURL+"Mobile/User/my_jilu";
    public final String WX_PAY=RunTime.BASEURL+"Mobile/Order/weixin";
    public final String ZFB_PAY=RunTime.BASEURL+"Mobile/Order/zhifubao";



    public void createOrder(Class clazz, final Operation.Listener shopListener) {
//        HttpTaskSubmit.executeTask(new HttpGetTask(), new HttpAbstractTask.OnResponseCallback() {
//            @Override
//            public void onResponse(String identifier, Object response) {
//                shopListener.tryReturn(identifier, response);
//            }
//        });
    }

    public void tryPay(Class clazz, Operation.Listener shopListener) {
    }

    public void tryRefresh(Class clazz, String listName, Operation.Listener shopListener) {
    }

}
