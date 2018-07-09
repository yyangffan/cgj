package com.lhkj.cgj.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityLllgameBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.LllGameLock;

/**
 * Created by 浩琦 on 2017/6/22.
 */

public class LllGameActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLllgameBinding lllgameBinding= DataBindingUtil.setContentView(this, R.layout.activity_lllgame);
        lllgameBinding.setLllGameLock(new LllGameLock(this,lllgameBinding));
        lllgameBinding.include.setAppBarLock(new AppBarLock(this,R.string.lllgame));
    }
}
