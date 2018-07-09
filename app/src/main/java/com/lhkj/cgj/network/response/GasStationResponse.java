package com.lhkj.cgj.network.response;

import java.util.List;

/**
 * 创建日期:2017/9/27 on 15:28
 * 描述:
 * 作者:郭士超
 * QQ:1169380200
 */

public class GasStationResponse extends HttpResponse{
    public List<Info> info;

    public class Info{
        public String admin_id;
        public String name;

    }
}
