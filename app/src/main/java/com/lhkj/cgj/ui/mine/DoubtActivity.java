package com.lhkj.cgj.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.widget.TextView;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityDoubtBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.DoubtLock;

/**
 * 创建日期:2017/10/12 on 16:09
 * 描述:签到帮助
 * 作者:郭士超
 * QQ:1169380200
 */

public class DoubtActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDoubtBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_doubt);
        DoubtLock doubtLock = new DoubtLock(this, binding);
        binding.setDoubtLock(doubtLock);
        binding.include4.setAppBarLock(new AppBarLock(this,R.string.doubt,R.mipmap.icon_back,0,true,false));

    }

}
