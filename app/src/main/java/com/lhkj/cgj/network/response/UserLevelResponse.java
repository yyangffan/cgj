package com.lhkj.cgj.network.response;

/**
 * Created by 浩琦 on 2017/7/26.
 */

public class UserLevelResponse extends HttpResponse {
    public Info info;
    public String level_name;
    public class Info{
        public String level_name;
    }
}
