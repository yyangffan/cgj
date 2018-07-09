package com.lhkj.cgj.network.response;

/**
 * Created by 浩琦 on 2017/7/10.
 */

public class UserInfoResponse extends HttpResponse {
    public Info info;
    public class Info{
        public String head_pic;
        public String mobile;
        public String nickname;
        public String sex;
    }

}
