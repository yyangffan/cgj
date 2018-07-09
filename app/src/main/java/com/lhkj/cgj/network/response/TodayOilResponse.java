package com.lhkj.cgj.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 浩琦 on 2017/7/3.
 */

public class TodayOilResponse extends HttpResponse {

    /**
     * code : 200
     * info : [{"id":"3","you_num":"92","price":"6.4","admin_id":"1"},{"id":"5","you_num":"95","price":"6.8","admin_id":"1"},{"id":"6","you_num":"98","price":"7.3","admin_id":"1"}]
     */

    @SerializedName("info")
    private List<InfoBean> infoX = new ArrayList<InfoBean>();

    public List<InfoBean> getInfoX() {
        return infoX;
    }

    public void setInfoX(List<InfoBean> infoX) {
        this.infoX = infoX;
    }

    public class InfoBean {
        /**
         * id : 3
         * you_num : 92
         * price : 6.4
         * admin_id : 1
         */

        private String id;
        private String you_num;
        private String price;
        private String admin_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getYou_num() {
            return you_num;
        }

        public void setYou_num(String you_num) {
            this.you_num = you_num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }
    }
}
