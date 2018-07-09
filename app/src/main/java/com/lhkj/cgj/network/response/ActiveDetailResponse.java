package com.lhkj.cgj.network.response;

/**
 * Created by user on 2018/4/13.
 */

public class ActiveDetailResponse extends HttpResponse {

    /**
     * code : 200
     * info : {"id":11,"admin_id":13,"title":"或掉进洒在123","img_url":"/Public/upload/uploads/2018-04-12/5acef3eb26d4f.png","content":"&lt;p&gt;阿三嘎斯发生过附属中学123124学生学习&lt;/p&gt;","state":1,"add_time":"1523512299","fb_time":"1523376000","s_time":"1523289600","o_time":"1523980800"}
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
         * id : 11
         * admin_id : 13
         * title : 或掉进洒在123
         * img_url : /Public/upload/uploads/2018-04-12/5acef3eb26d4f.png
         * content : &lt;p&gt;阿三嘎斯发生过附属中学123124学生学习&lt;/p&gt;
         * state : 1
         * add_time : 1523512299
         * fb_time : 1523376000
         * s_time : 1523289600
         * o_time : 1523980800
         */

        private int id;
        private int admin_id;
        private String title;
        private String img_url;
        private String content;
        private int state;
        private String add_time;
        private String fb_time;
        private String s_time;
        private String o_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(int admin_id) {
            this.admin_id = admin_id;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getFb_time() {
            return fb_time;
        }

        public void setFb_time(String fb_time) {
            this.fb_time = fb_time;
        }

        public String getS_time() {
            return s_time;
        }

        public void setS_time(String s_time) {
            this.s_time = s_time;
        }

        public String getO_time() {
            return o_time;
        }

        public void setO_time(String o_time) {
            this.o_time = o_time;
        }
    }
}
