package com.lhkj.cgj.ui.shop;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityShopDetailsBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.ShopDetailsLock;

/**
 * Created by 浩琦 on 2017/6/20.
 */

public class ShopDetailsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShopDetailsBinding shopDetailsBinding= DataBindingUtil.setContentView(this, R.layout.activity_shop_details);
        shopDetailsBinding.setShopDetailsLock(new ShopDetailsLock(this,shopDetailsBinding));
        shopDetailsBinding.include.setAppBarLock(new AppBarLock(this,R.string.commodity,R.mipmap.icon_back,0,true,false));
    }
}
