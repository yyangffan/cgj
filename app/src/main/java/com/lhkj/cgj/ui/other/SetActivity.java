package com.lhkj.cgj.ui.other;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivitySetBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.SetLock;

/**
 * Created by 浩琦 on 2017/6/20.
 */

public class SetActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySetBinding setBinding= DataBindingUtil.setContentView(this, R.layout.activity_set);
        setBinding.setSetLock(new SetLock(this,setBinding));
        setBinding.include2.setAppBarLock(new AppBarLock(this,R.string.set,R.mipmap.icon_back,0,true,false));
    }
}
