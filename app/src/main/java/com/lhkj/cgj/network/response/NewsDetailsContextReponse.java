package com.lhkj.cgj.network.response;

/**
 * Created by 浩琦 on 2017/8/17.
 */

public class NewsDetailsContextReponse extends HttpResponse {

    /**
     * code : 200
     * info : {"article_id":"1272","title":"道路救援，有些东西你还不懂","img_url":"/Public/upload/Articles/2017/08-14/599167d9dbd4e.png","content":"<p><span style=\"color: rgb(25, 25, 25); font-family: &#39;PingFang SC&#39;, Arial, 微软雅黑, 宋体, simsun, sans-serif; line-height: 30px;  background-color: rgb(255, 255, 255);\">驾驶着爱车走在路上，突然\u201c趴窝\u201d啦，是前不着村后不着店，是走也走不了，推也推不动，这个时候您最需要的就是道路救援。<\/span><\/p><p><span style=\"color: rgb(25, 25, 25); font-family: &#39;PingFang SC&#39;, Arial, 微软雅黑, 宋体, simsun, sans-serif; line-height: 30px;  background-color: rgb(255, 255, 255);\">说起这道路救援，对于车友们来说是非常重要的，因为大多数车友买车，其目的大多数，一、作为代步工具，二、为了面子（别人都有车自己也要有），很少一部分人是会开车还会养车修车的，这样就导致大多数车友平时对爱车的维护包养不完全，爱车就容易在路上出现各种小问题而抛锚。这时道路救援就成为了我们的唯一的救星。<\/span><\/p><p><span style=\"color: rgb(25, 25, 25); font-family: &#39;PingFang SC&#39;, Arial, 微软雅黑, 宋体, simsun, sans-serif; line-height: 30px;  background-color: rgb(255, 255, 255);\"><img src=\"http://yph.177678.top/Public/upload/article/2017/08-14/59916819c8379.png\" title=\"QQ截图20170814170631.png\"/><\/span><\/p><p><span style=\"color: rgb(25, 25, 25); font-family: &#39;PingFang SC&#39;, Arial, 微软雅黑, 宋体, simsun, sans-serif; line-height: 30px;  background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(25, 25, 25); font-family: &#39;PingFang SC&#39;, Arial, 微软雅黑, 宋体, simsun, sans-serif; line-height: 30px;  background-color: rgb(255, 255, 255);\">早期的道路救援一般是一些高档汽车的售后、金融保险行业为一些高端会员客户提供配套的尊享的高端服务内容，到现在这种服务也已经发展到相当成熟、相当普遍的地步，服务质量和服务区域也相对成熟。<\/span><\/span><\/p><p><span style=\"color: rgb(25, 25, 25); font-family: &#39;PingFang SC&#39;, Arial, 微软雅黑, 宋体, simsun, sans-serif; line-height: 30px;  background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(25, 25, 25); font-family: &#39;PingFang SC&#39;, Arial, 微软雅黑, 宋体, simsun, sans-serif; line-height: 30px;  background-color: rgb(255, 255, 255);\">随着国人购买力的增加，汽车市场的增大，道路救援需求也随之增加。自上世纪90年代，国际化道路救援公司开始进入中国市场，随后中国自己的道路救援服务公司也相继诞生。目前道路救援的业务已经非常完善，不仅仅出现了会员制\u2014\u2014救援服务卡，只要交少量的钱，就可以在规定期限内享有免费的救援服务，而且也开始与大企业紧密合作，专为其机构或会员提供免费的服务。<\/span><\/span><\/p><p><span style=\"color: rgb(25, 25, 25); font-family: &#39;PingFang SC&#39;, Arial, 微软雅黑, 宋体, simsun, sans-serif; line-height: 30px;  background-color: rgb(255, 255, 255);\"><\/span><\/p><p style=\"border: 0px; margin-top: 0px; margin-bottom: 0px; padding: 10px 0px 20px; color: rgb(25, 25, 25); font-family: &#39;PingFang SC&#39;, Arial, 微软雅黑, 宋体, simsun, sans-serif; line-height: 30px; white-space: normal; background-color: rgb(255, 255, 255);\">道路救援的服务内容，包括，接电服务，紧急送油，紧急加水，更换轮胎，现场抢修，拖车牵引，事故救援等等一系列服务。<\/p><p style=\"border: 0px; margin-top: 0px; margin-bottom: 0px; padding: 10px 0px 20px; color: rgb(25, 25, 25); font-family: &#39;PingFang SC&#39;, Arial, 微软雅黑, 宋体, simsun, sans-serif; line-height: 30px; white-space: normal; background-color: rgb(255, 255, 255);\">如果您既不是某些机构的会员或客户，也没有购买相关的救援服务卡的话，个人去叫道路救援的话费用可是非常的昂贵的哦。<\/p><p><span style=\"color: rgb(25, 25, 25); font-family: &#39;PingFang SC&#39;, Arial, 微软雅黑, 宋体, simsun, sans-serif; line-height: 30px;  background-color: rgb(255, 255, 255);\"><br/><\/span><br/><\/p>","add_time":"2017-08-14 05:06","click":"2","view":"51","zhaiyao":"驾驶着爱车走在路上，突然\u201c趴窝\u201d啦，是前不着村后不着店，是走","is_click":0}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public class InfoBean {
        /**
         * article_id : 1272
         * title : 道路救援，有些东西你还不懂
         * img_url : /Public/upload/Articles/2017/08-14/599167d9dbd4e.png
         *content
         * add_time : 2017-08-14 05:06
         * click : 2
         * view : 51
         * zhaiyao : 驾驶着爱车走在路上，突然“趴窝”啦，是前不着村后不着店，是走
         * is_click : 0
         */

        private String article_id;
        private String title;
        private String img_url;
        private String content;
        private String add_time;
        private String click;
        private String view;
        private String zhaiyao;
        private int is_click;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getClick() {
            return click;
        }

        public void setClick(String click) {
            this.click = click;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getZhaiyao() {
            return zhaiyao;
        }

        public void setZhaiyao(String zhaiyao) {
            this.zhaiyao = zhaiyao;
        }

        public int getIs_click() {
            return is_click;
        }

        public void setIs_click(int is_click) {
            this.is_click = is_click;
        }
    }
}
