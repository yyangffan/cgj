package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/11.
 */

public class ShopCatResponse extends HttpResponse {
    public ArrayList<Info> info;
    public class Info{
        public String id;
        public String name;
        public String img_url;
    }
}
