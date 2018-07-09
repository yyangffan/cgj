package com.lhkj.cgj.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * 创建日期:2017/9/28 on 14:24
 * 描述:
 * 作者:郭士超
 * QQ:1169380200
 */

public class PayOrderReponse extends HttpResponse {

    public Data data;
    public String info;
    public String order_number;

    public static class Data {

        public String appid;
        public String noncestr;
        @SerializedName("package")
        public String packageX;
        public String prepayid;
        public String partnerid;
        public String timestamp;
        public String sign;

    }
}
