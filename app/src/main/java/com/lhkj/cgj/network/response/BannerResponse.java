package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/10.
 */

public class BannerResponse extends HttpResponse {

    public ArrayList<Info> info;
//    public boolean info;
    /**
     * code : 100
     * success : 获取失败
     * info : false
     */

    public class Info{
        public String img_url;
        public String link;
    }

}
