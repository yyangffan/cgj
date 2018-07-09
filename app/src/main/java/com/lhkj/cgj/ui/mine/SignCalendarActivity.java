package com.lhkj.cgj.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivitySigncalendarBinding;
import com.lhkj.cgj.lock.SignCalendarLock;

/**
 * Created by 浩琦 on 2017/6/23.
 */

public class SignCalendarActivity extends BaseActivity {

    private SignCalendarLock signCalendarLock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySigncalendarBinding signcalendarBinding = DataBindingUtil.setContentView(this, R.layout.activity_signcalendar);
        signCalendarLock = new SignCalendarLock(this, signcalendarBinding);
        signcalendarBinding.setSignCalendarLock(signCalendarLock);
    }


}
