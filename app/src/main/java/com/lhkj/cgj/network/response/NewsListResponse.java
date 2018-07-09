package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/10.
 */

public class NewsListResponse extends HttpResponse{
//    public boolean info;
    public ArrayList<Info> info;
    public class Info{
        public String article_id;
        public String title;
        public String content;
        public String add_time;
        public String img_url;
        public String click;
        public String view;
        public String zhaiyao;
        public String is_click;
    }
}
