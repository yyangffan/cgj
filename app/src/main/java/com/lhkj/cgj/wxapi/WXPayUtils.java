package com.lhkj.cgj.wxapi;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static net.sourceforge.simcpux.wxapi.WXEntryActivity.APP_ID;


/**
 * Created by 浩琦 on 2017/6/26.
 */

public class WXPayUtils {
    public void showWXPay(Context context){
        IWXAPI api = WXAPIFactory.createWXAPI(context, APP_ID, false);
        PayReq request = new PayReq();
        request.appId = "wx4c41b416265916cf";
        request.partnerId = "1900000109";
        request.prepayId= "1101000000140415649af9fc314aa427";
        request.packageValue = "Sign=WXPay";
        request.nonceStr= "1101000000140429eb40476f8896f4c9";
        request.timeStamp= "1398746574";
        request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
        api.sendReq(request);
    }

}
