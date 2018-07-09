package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/17.
 */

public class MyOrderResponse extends HttpResponse {
    public ArrayList<Info> info;
    public class Info{
        public String order_id;
        public String order_sn;
        public String goods_price;
        public String goods_id;
        public String original_img;
        public String add_time;
        public String pay_status;
        public String order_status;
        public String goods_name;
        public String user_money;
        public String integral;
        public String order_price;
        public String type;
        public String jifen_money;
        public String shop_price;
        public String jifen;
    }
}
