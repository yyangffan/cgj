package com.lhkj.cgj.network.response;

/**
 * Created by 浩琦 on 2017/7/10.
 */

public class MyQrResponse extends HttpResponse {
    public Info info;

    public class Info {
        public String evm;
        public String id;
        public String user_id;
    }

}
