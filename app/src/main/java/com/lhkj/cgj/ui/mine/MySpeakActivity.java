package com.lhkj.cgj.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityMyspeakBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.MySpeakLock;

/**
 * Created by 浩琦 on 2017/6/22.
 */

public class MySpeakActivity extends BaseActivity {

    private MySpeakLock mySpeakLock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMyspeakBinding myspeakBinding = DataBindingUtil.setContentView(this, R.layout.activity_myspeak);
        mySpeakLock = new MySpeakLock(this, myspeakBinding);
        myspeakBinding.setMySpeakLock(mySpeakLock);
        myspeakBinding.include.setAppBarLock(new AppBarLock(this, R.string.myspeak, R.mipmap.icon_back, 0, true, false));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mySpeakLock.refresh();
    }
}
