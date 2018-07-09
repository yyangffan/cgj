package com.lhkj.cgj.network.response;

/**
 * Created by user on 2018/4/12.
 */

public class ZixunDetailResponse extends HttpResponse {

    /**
     * info : {"id":"4","zx_title":"资讯2222222222222222222222222","zx_content":"&lt;p&gt;啊我却接连开解她&lt;/p&gt;","zx_state":"1","admin_id":"13"}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 4
         * zx_title : 资讯2222222222222222222222222
         * zx_content : &lt;p&gt;啊我却接连开解她&lt;/p&gt;
         * zx_state : 1
         * admin_id : 13
         */

        private String id;
        private String zx_title;
        private String zx_content;
        private String zx_state;
        private String admin_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getZx_title() {
            return zx_title;
        }

        public void setZx_title(String zx_title) {
            this.zx_title = zx_title;
        }

        public String getZx_content() {
            return zx_content;
        }

        public void setZx_content(String zx_content) {
            this.zx_content = zx_content;
        }

        public String getZx_state() {
            return zx_state;
        }

        public void setZx_state(String zx_state) {
            this.zx_state = zx_state;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }
    }
}
