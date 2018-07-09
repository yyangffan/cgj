package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/11.
 */

public class ShopResponse extends HttpResponse {
    public ArrayList<Info> info;
    public class Info{
        public String goods_id;
        public String goods_name;
        public String original_img;
        public String exchange_integral;
        public String shop_type;
        public String shop_price;
        public String store_count;
    }

}
