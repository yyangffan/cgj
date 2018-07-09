package com.lhkj.cgj.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityMyorderBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.MyOrderLock;

/**
 * Created by 浩琦 on 2017/6/21.
 */

public class MyOrderActivity extends BaseActivity {
    private MyOrderLock myOrderLock;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMyorderBinding myorderBinding= DataBindingUtil.setContentView(this, R.layout.activity_myorder);
        myOrderLock=new MyOrderLock(this,myorderBinding);
        myorderBinding.setMyOrderLock(myOrderLock);
        myorderBinding.include.setAppBarLock(new AppBarLock(this,R.string.myorder,R.mipmap.icon_back,0,true,false));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        myOrderLock.flush();
    }
}
