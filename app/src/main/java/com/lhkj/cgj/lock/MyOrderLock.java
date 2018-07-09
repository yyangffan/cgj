package com.lhkj.cgj.lock;

import android.content.Context;

import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ActivityMyorderBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.MyOrderResponse;
import com.lhkj.cgj.ui.mine.MyOrderAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/21.
 * 我的订单
 */

public class MyOrderLock {
    public MyOrderAdapter myOrderAdapter;
    private ArrayList<MyOrderItem> myOrderData;

    public MyOrderLock(final Context context, ActivityMyorderBinding myorderBinding) {
        myOrderData = new ArrayList();
        myOrderAdapter = new MyOrderAdapter(context, myOrderData, R.layout.myorder_item, BR.myOrderItem);
        getData();
    }

    public void getData() {
        myOrderData.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(MyOrderResponse.class, RunTime.operation.getMine().ORDER_LIST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                MyOrderResponse myorder = (MyOrderResponse) data;
                for (MyOrderResponse.Info info : myorder.info) {
                    String note = null;
                    boolean is = false;
                    switch (info.pay_status) {
                        case "0":
                            note = "去支付";
                            is = false;
                            break;
                        case "1":
                            note = "确认收货";
                            is = false;
                            break;
                    }
                    switch (info.order_status) {
                        case "0":
                            if (!info.pay_status.equals("1")) {
                                note = "去支付";
                                is = false;
                            }
                            break;
                        case "1":
                            note = "已收货";
                            is = true;
                            break;
                    }
                    Date date = new Date();
                    date.setTime(Long.parseLong(info.add_time + "000"));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    info.add_time = formatter.format(date);
                    MyOrderItem myOrderItem = new MyOrderItem(info.order_id, info.goods_name,
                            info.jifen_money, info.order_sn, info.add_time,
                            info.original_img, is, note, info.order_price, info.type,
                            info.shop_price, info.jifen);
                    myOrderItem.goods_id = info.goods_id;
                    myOrderData.add(myOrderItem);
                }
                myOrderAdapter.notifyDataSetChanged();
            }
        });
    }

    public void flush() {
        getData();
    }

    public class MyOrderItem {
        public MyOrderItem(String orderId, String orderName, String orderIntegral, String orderNum,
                           String orderTime, String orderUrl, boolean isPay, String note, String orderPay,
                           String type, String shop_price, String jifen) {
            this.orderId = orderId;
            this.orderName = orderName;
            this.orderIntegral = orderIntegral;
            //订单号
            this.orderNum = orderNum;
            this.orderTime = orderTime;
            this.orderUrl = orderUrl;
            this.isPay = isPay;
            this.orderText = note;
            //订单支付金额
            this.orderPay = "￥" + orderPay;
            this.type = type;
            if(type != null){
                if (type.equals("0")) {
                    this.shop_price = "￥" + orderPay;
                    this.jifen = "未用积分";
                } else if (type.equals("1")) {
                    this.shop_price = "￥" + orderPay;
                    this.jifen = "未用积分";
                } else if (type.equals("2")) {
                    this.shop_price = jifen + "积分" ;
                    this.jifen = jifen + "积分已抵" + orderIntegral + "元";
                } else {
                    this.shop_price = "￥" + orderPay;
                    this.jifen = jifen + "积分已抵" + orderIntegral + "元";
                }
            }else {
                this.shop_price = "￥" + orderPay;
                this.jifen = "未用积分";
            }
        }

        public String orderId;
        public String goods_id;
        public String orderName;
        public String orderIntegral;
        public String orderNum;
        public String orderTime;
        public String orderUrl;
        public String orderText;
        public String orderPay;
        public String type;
        public boolean isPay;
        public String shop_price;
        public String jifen;
    }
}
