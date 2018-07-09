package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/10.
 */

public class CarInfoResponse extends HttpResponse {

    public ArrayList<Info> info;

    public class Info {
        public String id;
        public String name;
        public String Id;
        public String brand_name;
        public String Letters;
        public String series_name;
        public String is_imported;

    }
}
