package com.lhkj.cgj.ui.bbs;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityActiveListBinding;
import com.lhkj.cgj.lock.ActiveListLock;
import com.lhkj.cgj.lock.AppBarLock;

/********************************************************************
  @version: 1.0.0
  @description: 主页优惠活动跳转的新界面--跟之前的区分开因为接口（完全）变了
  @author: user
  @time: 2018/4/13 10:05
  @变更历史:
********************************************************************/

public class ActiveListActivity extends BaseActivity {

    private ActivityActiveListBinding mBinding;
    private ActiveListLock mActiveListLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_active_list);
        mActiveListLock=new ActiveListLock(this,mBinding);
        mBinding.setLock(mActiveListLock);
        mBinding.include8.setAppBarLock(new AppBarLock(this,R.string.active));


    }
}
