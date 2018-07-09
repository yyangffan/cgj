package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */

public class RedbaoResponse extends HttpResponse{
    public ArrayList<Info> info;
    /**   red_id,red_name,coupon_name,money,xuman_price,use_end_time'
     * code : 100
     * success : 获取失败
     * info : false
     */

    public class Info{
        public String red_id;
        public String red_name;
        public String coupon_name;
        public String coupon_id;
        public String money;
        public String xuman_price;
        public String use_end_time;
        public String name;

        @Override
        public String toString() {
            return "Info{" +
                    "red_id='" + red_id + '\'' +
                    "name='" + name + '\'' +
                    ", red_name='" + red_name + '\'' +
                    ", coupon_name='" + coupon_name + '\'' +
                    ", money='" + money + '\'' +
                    ", xuman_price='" + xuman_price + '\'' +
                    ", use_end_time='" + use_end_time + '\'' +
                    '}';
        }
    }

}
