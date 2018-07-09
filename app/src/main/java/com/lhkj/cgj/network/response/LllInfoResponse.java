package com.lhkj.cgj.network.response;

import java.util.List;

/**
 * 创建日期:2017/9/29 on 11:06
 * 描述:积分详情
 * 作者:郭士超
 * QQ:1169380200
 */

public class LllInfoResponse extends HttpResponse {

    public List<Info> info;

    public class Info{
        public String money;
        public String add_time;
        public String pay_method;
    }
}
