package com.lhkj.cgj.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 浩琦 on 2017/6/30.
 */

public class BindListResponse extends HttpResponse {

    /**
     * code : 200
     * info : [{"admin_id":"2","name":"中国石化","lat":"38.038865","lng":"114.565835"},{"admin_id":"3","name":"中国石油","lat":"38.028861","lng":"114.546575"},{"admin_id":"7","name":"123加油站","lat":"38.063302","lng":"114.505756"},{"admin_id":"8","name":"加油站","lat":null,"lng":null}]
     */

    @SerializedName("info")
    private List<InfoBean> info;

    public List<InfoBean> getInfoX() {
        return info;
    }

    public void setInfoX(List<InfoBean> infoX) {
        this.info = infoX;
    }

    public class InfoBean {
        /**
         * admin_id : 2
         * name : 中国石化
         * lat : 38.038865
         * lng : 114.565835
         */

        private String admin_id;
        private String name;
        private String lat;
        private String lng;

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        @Override
        public String toString() {
            return "InfoBean{" +
                    "admin_id='" + admin_id + '\'' +
                    ", name='" + name + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    '}';
        }
    }

}
