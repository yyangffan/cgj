package com.lhkj.cgj.ui.main;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;

/**
 * 创建日期：2017/10/19 on 17:52
 * 描述：
 * 作者：郭士超
 * QQ：1169380200
 */

public class MyApplication extends Application {
    private static MyApplication myApplication = null;

    public static MyApplication getApplication() {
        if(myApplication == null){
            myApplication = new MyApplication();
        }
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "5acc7bd08f4a9d40ca000043");
          /*---------------Nohttp的初始化-------------------*/
        InitializationConfig config = InitializationConfig.newBuilder(this).connectionTimeout(30 * 1000).readTimeout(30 * 1000).retry(10).build();
        NoHttp.initialize(config);
    }
}