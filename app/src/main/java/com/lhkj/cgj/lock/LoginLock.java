package com.lhkj.cgj.lock;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.ui.login.ForgetActivity;
import com.lhkj.cgj.ui.login.LoginActivity;
import com.lhkj.cgj.ui.login.SignActivity;
import com.lhkj.cgj.ui.other.UMengSave;
import com.lhkj.cgj.utils.SharedPreferencesUtil;
import com.lhkj.cgj.utils.ToolUtils;

/**
 * Created by 浩琦 on 2017/6/20.
 * 登陆页面
 */

public class LoginLock {
    public LoginData loginData;
    private Context context;
    public LoginLock(Context context){
        this.context=context;
        loginData=new LoginData();
    }

    public void login(){
        if (ToolUtils.isMobileNO(loginData.loginName)){

            RunTime.setData(RunTime.LOGIN_ID, true);

            RunTime.operation.getMine().tryLogin(loginData.loginName, loginData.loginPwd,
                    new User.AuthorizationListener() {
                @Override
                public void authorization(boolean isOk) {
                    if (isOk){
                        ((LoginActivity)context).setResult(200);
                        ((LoginActivity)context).finish();
                        SharedPreferencesUtil.saveSharePreString(context,User.getUser().SAVE_NAME,loginData.loginName);
                        SharedPreferencesUtil.saveSharePreString(context,User.getUser().SAVE_PWD,loginData.loginPwd);
                        UMengSave.saveMsg(loginData.loginName,loginData.loginPwd);
                    }else {
//                        Toast.makeText(context, "登陆失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(context, "请输入合法的手机号", Toast.LENGTH_SHORT).show();
        }
    }
    public void sign(){
        ((LoginActivity)context).startActivityForResult(new Intent(((LoginActivity)context),SignActivity.class),200);
    }
    public void forget(){
        ((LoginActivity)context).startActivity(ForgetActivity.class);
    }
    public class LoginData{
        public String loginName="";
        public String loginPwd="";
    }
}
