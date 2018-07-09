package com.lhkj.cgj.ui.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityLoginBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.LoginLock;

/**
 * Created by 浩琦 on 2017/6/20.
 */

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding mLoginBinding;
    private LoginLock mLoginLock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mLoginLock = new LoginLock(this);
        mLoginBinding.setLoginLock(mLoginLock);
        mLoginBinding.include3.setAppBarLock(new AppBarLock(this, R.string.login, R.mipmap.icon_back, 0, true, false));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 200:
                    if (data != null) {
                        String phone = data.getStringExtra("phone");
                        mLoginLock.loginData.loginName=phone;
                        mLoginBinding.loginPhone.setText(phone);
                        LoginActivity.this.finish();
                    }
                    break;
            }
        }
    }

}
