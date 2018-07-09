package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/10.
 */

public class CatListResponse extends HttpResponse {
    public ArrayList<Info> info = new ArrayList<Info>();
    public class Info{
        public String cat_id;
        public String cat_name;
    }
}
