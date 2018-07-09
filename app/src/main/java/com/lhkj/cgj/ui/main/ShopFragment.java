package com.lhkj.cgj.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.FragmentShopBinding;
import com.lhkj.cgj.lock.ShopLock;

/**
 * Created by 浩琦 on 2017/6/19.
 */

public class ShopFragment extends Fragment {
    private ShopLock shopLock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentShopBinding shopBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop, container, false);
        shopLock = new ShopLock(getActivity(), shopBinding);
        shopBinding.setShopLock(shopLock);
        return shopBinding.getRoot();
    }

    public void flushShop() {
        if (shopLock != null) {
            shopLock.flushShop();
        }
    }
}
