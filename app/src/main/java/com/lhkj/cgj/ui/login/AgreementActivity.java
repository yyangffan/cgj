package com.lhkj.cgj.ui.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityAgreementBinding;
import com.lhkj.cgj.lock.AgreementLock;
import com.lhkj.cgj.lock.AppBarLock;


public class AgreementActivity extends BaseActivity {

    private ActivityAgreementBinding mMbinding;
    private AgreementLock mAgreementLock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMbinding = DataBindingUtil.setContentView(this, R.layout.activity_agreement);
        mAgreementLock=new AgreementLock(mMbinding,this);
        mMbinding.include3.setAppBarLock(new AppBarLock(this,R.string.agreement,R.mipmap.icon_back,0,true,false));
    }
}
