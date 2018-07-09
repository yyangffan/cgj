package com.lhkj.cgj.network.response;

import java.util.List;

/**
 * Created by 浩琦 on 2017/8/7.
 */

public class ShopOrderReponse extends HttpResponse {

    /**
     * code : 200
     * success : 成功
     * info : {"goods_id":"144","goods_name":"齐全","img":[{"img_url":"http://yph.177678.top/Public/upload/goods/2017/06-27/5952205f7ab0e.jpg"},{"img_url":"http://yph.177678.top/Public/upload/goods/2017/06-27/59522021c832e.jpg"}],"exchang_integral":null,"is_hot":"1","goods_content":"&lt;p&gt;这里是商品详情啊，哈哈哈哈哈哈&lt;/p&gt;","shop_price":"29.00","order_sn":"H16190127487","type":"1","jifen":"100","order_id":"985"}
     */

    public InfoBean info;


    public  class InfoBean {
        /**
         * goods_id : 144
         * goods_name : 齐全
         * img : [{"img_url":"http://yph.177678.top/Public/upload/goods/2017/06-27/5952205f7ab0e.jpg"},{"img_url":"http://yph.177678.top/Public/upload/goods/2017/06-27/59522021c832e.jpg"}]
         * exchang_integral : null
         * is_hot : 1
         * goods_content : &lt;p&gt;这里是商品详情啊，哈哈哈哈哈哈&lt;/p&gt;
         * shop_price : 29.00
         * order_sn : H16190127487
         * type : 1
         * jifen : 100
         * order_id : 985
         */

        public String goods_id;
        public String goods_name;
        public String exchang_integral;
        public String is_hot;
        public String goods_content;
        public String shop_price;
        public String order_sn;
        public String type;
        public String jifen;
        public String order_id;
        public List<ImgBean> img;


        public  class ImgBean {
            /**
             * img_url : http://yph.177678.top/Public/upload/goods/2017/06-27/5952205f7ab0e.jpg
             */

            private String img_url;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }
    }
}
