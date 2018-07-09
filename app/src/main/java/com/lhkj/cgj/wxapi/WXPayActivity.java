package com.lhkj.cgj.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by 浩琦 on 2017/7/24.
 */
public class WXPayActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toWXpay();
    }

    public void toWXpay(){
        //  商户APP工程中引入微信JAR包，调用API前，需要先向微信注册您的APPID，代码如下：
        final IWXAPI api = WXAPIFactory.createWXAPI(getApplicationContext(), null);
// 将该app注册到微信
        api.registerApp("wxd930ea5d5a258f4f");
//        IWXAPI api=null;
        PayReq request = new PayReq();
        request.appId ="wx4c41b416265916cf";
        request.partnerId ="1900000109";
        request.prepayId="1101000000140415649af9fc314aa427";
        request.packageValue ="Sign=WXPay";
        request.nonceStr="1101000000140429eb40476f8896f4c9";
        request.timeStamp="1398746574";
        request.sign="7FFECB600D7157C5AA49810D2D8F28BC2811827B";
        api.sendReq(request);
    }

}