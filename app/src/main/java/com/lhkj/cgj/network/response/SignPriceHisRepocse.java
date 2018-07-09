package com.lhkj.cgj.network.response;

import java.util.List;

/**
 * Created by 浩琦 on 2017/8/15.
 */

public class SignPriceHisRepocse extends HttpResponse {

    /**
     * code : 200
     * info : [{"id":"51","state":"2","send_time":"1502760789","use_time":"1502760789","type":"2","jifen_num":"10","goods_id":null}]
     */

    public List<InfoBean> info;


    public class InfoBean {
        /**
         * id : 51
         * state : 2
         * send_time : 1502760789
         * use_time : 1502760789
         * type : 2
         * jifen_num : 10
         * goods_id : null
         */
        public String goods_name;
        public String goods_img;
        public String id;
        public String state;
        public String send_time;
        public String use_time;
        public String type;
        public String jifen_num;
        public String goods_id;

    }
}
