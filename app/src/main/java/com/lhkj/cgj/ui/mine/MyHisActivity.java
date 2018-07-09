package com.lhkj.cgj.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityMyhisBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.MyHisLock;

/**
 * Created by 浩琦 on 2017/6/22.
 */

public class MyHisActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMyhisBinding myhisBinding= DataBindingUtil.setContentView(this, R.layout.activity_myhis);
        myhisBinding.setMyHisLock(new MyHisLock(this,myhisBinding));
        myhisBinding.include.setAppBarLock(new AppBarLock(this,R.string.myhis));
    }
}
