package com.lhkj.cgj.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityMycardBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.MyCardLock;

/**
 * Created by 浩琦 on 2017/6/22.
 */

public class MyCardActivity extends BaseActivity {
    private  ActivityMycardBinding mycardBinding;
    private MyCardLock myCardLock;
    private int pos=-1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mycardBinding= DataBindingUtil.setContentView(this, R.layout.activity_mycard);
        myCardLock=new MyCardLock(this,mycardBinding);
        mycardBinding.setMyCardLock(myCardLock);
        mycardBinding.include5.setAppBarLock(new AppBarLock(this,R.string.mycard,R.mipmap.icon_back,0,true,false));
        if(this.getIntent()!=null) {
            pos = this.getIntent().getIntExtra("pos", -1);
        }
    }

    public Intent getCallIntent(Context context,int pos){
        Intent intent=new Intent(context,this.getClass());
        intent.putExtra("pos",pos);
        return intent;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        myCardLock.couponListCat();
        myCardLock.refreshList();
    }
}
