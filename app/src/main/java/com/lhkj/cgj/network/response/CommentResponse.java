package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/12.
 */

public class CommentResponse extends HttpResponse {
    public ArrayList<Info> info;
    public class Info{
        public String article_id;
        public String comment_id;
        public String title;
        public String content;
        public String is_read;
        public String img_url;
        public String zhaiyao;
        public String zan;
    }
}
