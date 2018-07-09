package com.lhkj.cgj.network.response;

/**
 * Created by 浩琦 on 2017/8/11.
 */

public class ProtocolReponse extends HttpResponse {
    public Info info = new Info();
    public class Info{
        public String content = "";
        public String title = "";
    }
}
