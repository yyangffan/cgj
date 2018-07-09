package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/27.
 */

public class CalenderListReponse extends HttpResponse {
    public ArrayList<Info> info;
    public String tianshu;

    public class Info {
        public String day;
        public String qiandao_time;

    }
}
