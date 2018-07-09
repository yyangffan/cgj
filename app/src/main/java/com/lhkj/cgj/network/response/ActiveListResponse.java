package com.lhkj.cgj.network.response;

import java.util.List;

/**
 * Created by user on 2018/4/13.
 */

public class ActiveListResponse extends HttpResponse {

    /**
     * code : 200
     * info : [{"id":"11","title":"或掉进洒在123","img_url":"/Public/upload/uploads/2018-04-12/5acef3eb26d4f.png"},{"id":"12","title":"houding22222","img_url":"/Public/upload/uploads/2018-04-12/5acef3f997177.jpg"}]
     */

    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 11
         * title : 或掉进洒在123
         * img_url : /Public/upload/uploads/2018-04-12/5acef3eb26d4f.png
         */

        private String id;
        private String title;
        private String img_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}
