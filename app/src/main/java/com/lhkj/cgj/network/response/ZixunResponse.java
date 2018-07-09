package com.lhkj.cgj.network.response;

import java.util.List;

/**
 * Created by user on 2018/4/12.
 */

public class ZixunResponse extends HttpResponse{


    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 3
         * zx_title : 资讯1111111111111111111
         */

        private String id;
        private String zx_title;

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
    }
}
