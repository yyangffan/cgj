package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/17.
 */

public class CouponResponse extends HttpResponse {
    public ArrayList<Info> info;
    public class Info{
        public String cat_id;
        public String cate_name;
    }
}
