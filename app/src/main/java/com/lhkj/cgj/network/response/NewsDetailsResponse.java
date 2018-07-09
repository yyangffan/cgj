package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/12.
 */

public class NewsDetailsResponse extends HttpResponse {
    public ArrayList<Info> info;
    public class Info{
        public String comment_id;
        public String contents;
        public String click;
        public String user_id;
        public String head_pic;
        public String nickname;
        public String is_click;
        public ArrayList<Reply> reply;
    }
    public class Reply{
        public String comment_id;
        public String contents;
        public String click;
        public String is_click;
    }
}
