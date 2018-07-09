package com.lhkj.cgj.network.response;

/**
 * Created by 浩琦 on 2017/7/22.
 */

public class AdsenseReponse extends HttpResponse {
    public Info info;

    public class Info {
        public String img_url;
        public String link;

    }
}
