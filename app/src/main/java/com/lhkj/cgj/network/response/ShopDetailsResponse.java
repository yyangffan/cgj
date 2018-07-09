package com.lhkj.cgj.network.response;

import java.util.List;

/**
 * Created by 浩琦 on 2017/7/11.
 */

public class ShopDetailsResponse extends HttpResponse {
    public Info info;

    public class Info {
        public String goods_id;
        public String goods_name;
        public List<String> img;
        public String exchange_integral;
        public String is_hot;
        public String jifen;
        public String goods_content;
        public String shop_price;
        public String order_sn;
        public String points;
        public String shop_type;
        public String store_count;
    }
}
