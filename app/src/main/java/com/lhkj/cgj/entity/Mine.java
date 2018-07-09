package com.lhkj.cgj.entity;

import android.util.Log;
import android.widget.Toast;

import com.lhkj.cgj.base.network.HttpAbstractTask;
import com.lhkj.cgj.network.request.HttpPostTask;
import com.lhkj.cgj.network.response.HttpResponse;
import com.lhkj.cgj.network.response.NowLllReponse;
import com.lhkj.cgj.network.response.SuccessResponse;
import com.lhkj.cgj.network.response.UserResponse;
import com.lhkj.cgj.ui.main.MyApplication;
import com.lhkj.cgj.utils.HttpTaskSubmit;
import com.lhkj.cgj.utils.SetJPushAlias;
import com.lhkj.cgj.utils.SharedPreferencesUtil;

import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/28.
 */

public class Mine {
    private final String SEND_CODE = RunTime.BASEURL + "Mobile/Register/send_code";
    private final String SEND_FORGET_CODE = RunTime.BASEURL + "Mobile/User/send_edit_code";
    private final String LOGIN = RunTime.BASEURL + "Mobile/Register/save_login";
    private final String REGISTER = RunTime.BASEURL + "Mobile/Register/register";
    private final String FORGET = RunTime.BASEURL + "Mobile/User/edit_pwd";
    public final String COUPON_LIST = RunTime.BASEURL + "Mobile/Coupon/coupon_cate";
    public final String COUPON_POST = RunTime.BASEURL + "Mobile/Coupon/coupon_list";
    public final String ORDER_LIST = RunTime.BASEURL + "Mobile/Order/my_order";
    public final String GAS_LIST = RunTime.BASEURL + "Mobile/User/gain_oil";
    public final String OIL_BANNER = RunTime.BASEURL + "Mobile/User/gain_oil_banner";
    public final String COUPON_BANNER = RunTime.BASEURL + "Mobile/Coupon/gain_coupon_banner";
    public final String GAME_BANNER = RunTime.BASEURL + "Mobile/User/get_game_banner";
    public final String CAR_POST = RunTime.BASEURL + "Mobile/User/cart_info";
    public final String GAME_LIST = "";
    public final String SPEAK_LIST = RunTime.BASEURL + "Mobile/User/my_comment";
    public final String HIS_LIST = RunTime.BASEURL + "Mobile/User/oil_jilu";
    public final String USER_SUB = RunTime.BASEURL + "Mobile/User/complete_user_post";
    public final String CAR_POST_ADD = RunTime.BASEURL + "Mobile/User/get_jiancheng";
    public final String CAR_POST_BRAND = RunTime.BASEURL + "Mobile/User/car_brand_list";
    public final String CAR_POST_BRAND_AFT = RunTime.BASEURL + "Mobile/User/get_car_mark  ";
    public final String CAR_SUB = RunTime.BASEURL + "Mobile/User/complete_car";
    //    public final String SHARE_CODE=RunTime.BASEURL+"Mobile/Upload/wem";
    public final String SIGN_CALENDER = RunTime.BASEURL + "Mobile/QianDao/qiandao";
    public final String PROMPT = RunTime.BASEURL + "Mobile/User/weidu_num";
    public final String USER_LEVEL = RunTime.BASEURL + "Mobile/User/get_user_level";
    public final String CALENDER_LIST = RunTime.BASEURL + "Mobile/QianDao/my_qiandao";
    public final String CALENDER_PRICE_LIST = RunTime.BASEURL + "Mobile/QianDao/jiangli_list";
    public final String CALENDER_DAY = RunTime.BASEURL + "Mobile/QianDao/my_days";
    public final String SIGN_LLL = RunTime.BASEURL + "Mobile/QianDao/get_prize";
    public final String SHARE_USER_LIST = RunTime.BASEURL + "Mobile/Onepage/index";
    public final String MY_SHARE_LIST = RunTime.BASEURL + "Mobile/Upload/my_yaoqing";
    public final String MY_LLL_INFO = RunTime.BASEURL + "Mobile/User/jifen_detail";
    public final String MY_PRICE_LIST = RunTime.BASEURL + "Mobile/QianDao/my_prize_list";
    public final String ABOUT = RunTime.BASEURL + "Mobile/Index/about_me";
    public final String PROTOCOL = RunTime.BASEURL + "Mobile/Index/user_xieyi";
    public final String DOUBT = RunTime.BASEURL + "Mobile/QianDao/shuoming";
    public final String REDBAO = RunTime.BASEURL + "mobile/Napi/red_re";
    public final String GETRANLIAO = RunTime.BASEURL + "work/index/get_oil_type";
    public final String COPZHUANZENG = RunTime.BASEURL + "mobile/napi/cop_zhuanzeng";
    public final String COPZZOK = RunTime.BASEURL + "mobile/napi/cop_zz_ok";


    public void tryLogin(String name, String pwd, final User.AuthorizationListener listener) {
        HashMap hashMap = new HashMap();
        hashMap.put("mobile", name);
        hashMap.put("password", pwd);

        if (RunTime.getRunTime(RunTime.LOGIN_ID) != null && !(boolean) RunTime.getRunTime(RunTime.LOGIN_ID)) {
            hashMap.put("tpye", "1");
            RunTime.setData(RunTime.LOGIN_ID, false);
        }


        HttpPostTask postTask = new HttpPostTask(LOGIN, hashMap, hashMap.hashCode(), UserResponse.class);
        HttpTaskSubmit.executeTask(postTask, new HttpAbstractTask.OnResponseCallback() {
            @Override
            public void onResponse(int identifier, Object response) {
                UserResponse userResponse = (UserResponse) response;
                if (userResponse.getResultcode().equals("200")) {
                    SharedPreferencesUtil.saveStringData(MyApplication.getApplication(),"user_id",userResponse.getInfo().getUser_id());
                    Log.e("登录时的User_id",userResponse.getInfo().getUser_id());
                    User.getUser().initUser(userResponse);
                    SharedPreferencesUtil.saveSharePreString(MyApplication.getApplication(),
                            "token", userResponse.getInfo().getToken());
                    SetJPushAlias setJPushAlias = new SetJPushAlias(userResponse.getInfo().getUser_id(), MyApplication.getApplication());
                    setJPushAlias.setAlias();
                    listener.authorization(true);
                } else {
                    Toast.makeText(MyApplication.getApplication(),userResponse.getResultmsg(), Toast.LENGTH_SHORT).show();
                    listener.authorization(false);
                }

            }
        });
    }

    public void trySign(String name, String code, String pwd, String share, String signDiquId, String plateNumber,String ranliao, final User.AuthorizationListener listener) {
        HashMap hashMap = new HashMap();
        hashMap.put("mobile", name);
        hashMap.put("password", pwd);
        hashMap.put("auth_code", code);
        hashMap.put("porn", share);
        hashMap.put("diqu", signDiquId);
        hashMap.put("number", plateNumber);
        hashMap.put("oil_type", ranliao);

        HttpPostTask postTask = new HttpPostTask(REGISTER, hashMap, hashMap.hashCode(), UserResponse.class);
        HttpTaskSubmit.executeTask(postTask, new HttpAbstractTask.OnResponseCallback() {
            @Override
            public void onResponse(int identifier, Object response) {
                UserResponse userResponse = (UserResponse) response;
                if (userResponse.getResultcode().equals("200")) {
//                    User.getUser().initUser(userResponse);
                    listener.authorization(true);
                } else {
                    listener.authorization(false);
                    Toast.makeText(MyApplication.getApplication(),userResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void tryForget(String name, String code, String pwd, String repwd, User.AuthorizationListener listener) {
        HashMap hashMap = new HashMap();
        hashMap.put("mobile", name);
        hashMap.put("password", pwd);
        hashMap.put("auth_code", code);
        HttpPostTask postTask = new HttpPostTask(FORGET, hashMap, hashMap.hashCode(), SuccessResponse.class);
        sub(postTask, listener);
    }

    public void trySendCode(String phone, final User.AuthorizationListener listener) {
        HashMap hashMap = new HashMap();
        hashMap.put("mobile", phone);
        HttpPostTask postTask = new HttpPostTask(SEND_CODE, hashMap, hashMap.hashCode(), NowLllReponse.class);
        HttpTaskSubmit.executeTask(postTask, new HttpAbstractTask.OnResponseCallback() {
            @Override
            public void onResponse(int identifier, Object response) {
                NowLllReponse nowCode = (NowLllReponse) response;
                if (nowCode.getResultcode().equals("200")) {
                    RunTime.setData(RunTime.SIGN_CODE, nowCode.info);
                    listener.authorization(true);
                } else {
                    listener.authorization(false);
                }
            }
        });
    }

    public void trySendForCode(String phone, User.AuthorizationListener listener) {
        HashMap hashMap = new HashMap();
        hashMap.put("mobile", phone);
        HttpPostTask postTask = new HttpPostTask(SEND_FORGET_CODE, hashMap, hashMap.hashCode(), SuccessResponse.class);
        sub(postTask, listener);
    }

    private void sub(HttpPostTask httpPostTask, final User.AuthorizationListener listener) {
        HttpTaskSubmit.executeTask(httpPostTask, new HttpAbstractTask.OnResponseCallback() {
            @Override
            public void onResponse(int identifier, Object response) {
                HttpResponse httpResponse = (HttpResponse) response;
                if (httpResponse.getResultcode().equals("200")) {
                    Log.i("aaaa", httpResponse.getResultcode());
                    listener.authorization(true);
                } else {
                    Toast.makeText(MyApplication.getApplication(),"发送失败", Toast.LENGTH_SHORT).show();
                    listener.authorization(false);
                }
            }
        });
    }


}
