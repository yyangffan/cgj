package com.lhkj.cgj.ui.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivitySignBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.SignLock;

/**
 * Created by 浩琦 on 2017/6/20.
 */

public class SignActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignBinding signBinding= DataBindingUtil.setContentView(this, R.layout.activity_sign);
        signBinding.setSignLock(new SignLock(this,signBinding));
        signBinding.include3.setAppBarLock(new AppBarLock(this,R.string.sign,R.mipmap.icon_back,0,true,false));
    }
}
