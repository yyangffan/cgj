package com.lhkj.cgj.network.response;

/**
 * 创建日期:2017/9/28 on 9:50
 * 描述:
 * 作者:郭士超
 * QQ:1169380200
 */

public class StationInfoReponse extends HttpResponse {

    public Info info;

    public class Info{
        public String admin_id;
        public String content;
    }

}
