package com.lhkj.cgj.network.response;

/**
 * 创建日期:2017/9/22 on 11:47
 * 描述:
 * 作者:郭士超
 * QQ:1169380200
 */

public class NewsResponse extends HttpResponse {
    public Info info;

    public class Info {
        public String article_id;
        public String title;
        public String img_url;
        public String content;
        public String is_click;
        public String view;
        public String add_time;
        public String click;
        public String zhaiyao;
    }
}
