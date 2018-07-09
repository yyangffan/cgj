package com.lhkj.cgj.network.response;

import java.util.List;

/**
 * Created by 浩琦 on 2017/7/25.
 */

public class SignCalenderReponse extends HttpResponse {


    /**
     * code : 200
     * info : [{"id":"3","uid":"54","type":"1","goods_id":"144","jifen":null,"time":"1502182731","goods_info":{"img_url":"http://yph.177678.top","goods_name":null,"jifenlv":"1","time":"2017-08-08 16:58:51"}},{"id":"4","uid":"54","type":"2","goods_id":null,"jifen":"200","time":"1502182731","goods_info":{"img_url":"http://yph.177678.top","goods_name":null,"jifenlv":"1","time":"2017-08-08 16:58:51"}},{"id":"5","uid":"54","type":"1","goods_id":"189","jifen":null,"time":"1502718850","goods_info":{"img_url":"http://yph.177678.top/Public/upload/goods/2017/08-14/59917ea2452f5.jpg","goods_name":"0℃ 汽车玻璃水 -25℃ 雨刮水 车用前挡风玻璃液 2升 雨刮液防冻玻璃水剂清洗剂 0度夏季玻璃水【买4送1】","jifenlv":"1","time":"2017-08-14 21:54:10","money":"300","string":"已使用300积分"}},{"id":"8","uid":"54","type":"2","goods_id":null,"jifen":"10","time":"1502760789","goods_info":{"img_url":"http://yph.177678.top","goods_name":null,"jifenlv":"1","time":"2017-08-15 09:33:09"}},{"id":"10","uid":"54","type":"1","goods_id":"189","jifen":null,"time":"1502760877","goods_info":{"img_url":"http://yph.177678.top/Public/upload/goods/2017/08-14/59917ea2452f5.jpg","goods_name":"0℃ 汽车玻璃水 -25℃ 雨刮水 车用前挡风玻璃液 2升 雨刮液防冻玻璃水剂清洗剂 0度夏季玻璃水【买4送1】","jifenlv":"1","time":"2017-08-15 09:34:37","money":"300","string":"已使用300积分"}}]
     * jifenlv : 1
     */

    public String jifenlv;
    public List<InfoBean> info;

    public class InfoBean {
        /**
         * id : 3
         * uid : 54
         * type : 1
         * goods_id : 144
         * jifen : null
         * time : 1502182731
         * goods_info : {"img_url":"http://yph.177678.top","goods_name":null,"jifenlv":"1","time":"2017-08-08 16:58:51"}
         */
        public String id;
        public String uid;
        public String type;
        public String goods_id;
        public String jifen;
        public String time;
        public GoodsInfoBean goods_info;

        public class GoodsInfoBean {
            /**
             * img_url : http://yph.177678.top
             * goods_name : null
             * jifenlv : 1
             * time : 2017-08-08 16:58:51
             */

            public String img_url;
            public String goods_name;
            public String jifenlv;
            public String time;
            public String string;
            public String money;

        }
    }
}
