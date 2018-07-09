package com.lhkj.cgj.network.response;

import java.util.List;

/**
 * Created by 浩琦 on 2017/7/27.
 */

public class CalenderPriceReponse extends HttpResponse {


    /**
     * code : 200
     * success : 获取成功
     * info : [{"id":"5","link_day":"1","type":"2","jifen_num":"10","goods_id":null,"admin_id":"2"},{"id":"6","link_day":"3","type":"2","jifen_num":"20","goods_id":null,"admin_id":"2"},{"id":"7","link_day":"5","type":"1","jifen_num":null,"goods_id":"147","admin_id":"2","goods_img":"http://yph.177678.top/Public/upload/goods/2017/06-27/59522021c832e.jpg"}]
     */

    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public  class InfoBean {
        /**
         * id : 5
         * link_day : 1
         * type : 2
         * jifen_num : 10
         * goods_id : null
         * admin_id : 2
         * goods_img : http://yph.177678.top/Public/upload/goods/2017/06-27/59522021c832e.jpg
         */

        private String id;
        private String link_day;
        private String type;
        private String jifen_num;
        private String goods_id;
        private String admin_id;
        private String goods_img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLink_day() {
            return link_day;
        }

        public void setLink_day(String link_day) {
            this.link_day = link_day;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getJifen_num() {
            return jifen_num;
        }

        public void setJifen_num(String jifen_num) {
            this.jifen_num = jifen_num;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }
    }
}
