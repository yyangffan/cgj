package com.lhkj.cgj.entity;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/21.
 * 运行数据临时储存
 */

public class RunTime {

    public static final int SHOPID = 10000;
    public static final int SHOP_TYPE = 10001;
    public static final int NEWSTYPE = 11000;
    public static final int NEWID = 11001;
    public static final int CAT_ID = 11002;
    public static final int CODE_TYPE = 11003;
    public static final int ORDER_NUM = 11004;
    public static final int COUPON_ID = 11005;
    public static final int IS_CODE_READ = 11006;
    public static final int SIGN_ID = 11007;
    public static final int SIGN_URL = 11008;
    public static final int SIGN_CODE = 11009;
    public static final int ORDER_PAY = 10010;
    public static final int STATION_ID = 11011;
    public static final int STATE_ID = 110012;
    public static final int ORDER_ID = 110013;
    public static final int LOGIN_ID = 110014;
//    public static final String BASEURL="http://yph.177678.top/";
//    public static final String BASEURL = "http://yph.linghangnc.com/";
    public static final String BASEURL = "http://www.hbbfjt.top/";
    private static HashMap<Integer, Object> runTime = new HashMap<>();
    public static Operation operation;
    public static Context appContext;

    public static Object getRunTime(Integer key) {
        return runTime.get(key);
    }

    public static void setData(Integer key, Object valus) {
        runTime.put(key, valus);
    }
}
