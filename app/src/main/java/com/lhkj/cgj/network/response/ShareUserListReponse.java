package com.lhkj.cgj.network.response;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/8/10.
 */

public class ShareUserListReponse extends HttpResponse {
//    info   :     当前用户id     parent_id       
//                         所邀请人的id   user_id
//             赠送的积分数      jifen
//    注册时间      time
//   所邀请人的昵称          nickname
//            所邀请人的手机号码    mobile

    public ArrayList<Info> info;
    public class Info{
        public String parent_id;
        public String user_id;
        public String nickname;
        public String mobile;
        public String jifen;
        public String time;
    }
}
