package com.lhkj.cgj.ui.main;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.ShopItemBinding;
import com.lhkj.cgj.lock.ShopLock;

import java.util.List;

/**
 * Created by 浩琦 on 2017/6/19.
 */

public class ShopListAdapter extends BaseTopAdapter {
    public ShopListAdapter(Context context, List list, int layoutId, int variableId) {
        super(context, list, layoutId, variableId);
    }

    @Override
    protected void subClassTask(ViewDataBinding binding, int position) {
        super.subClassTask(binding, position);
        Glide.with(context).load(((ShopLock.ShopItem)list.get(position)).url).into(((ShopItemBinding)binding).shopIms);
    }
}
