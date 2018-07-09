package com.lhkj.cgj.ui.main;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.HomeItem1Binding;
import com.lhkj.cgj.lock.HomeLock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 浩琦 on 2017/6/19.
 */

public class HomeNewsAdapter extends BaseTopAdapter {
    private ArrayList<HomeLock.HomeItem1> list;
    private Context context;
    public HomeNewsAdapter(Context context, List list, int layoutId, int variableId) {
        super(context, list, layoutId, variableId);
        this.list=(ArrayList) list;
        this.context=context;
    }

    @Override
    protected void subClassTask(ViewDataBinding binding, int position) {
        super.subClassTask(binding, position);
        HomeItem1Binding homeItem1Binding=(HomeItem1Binding)binding;
        String url=list.get(position).imsUrl;
        Glide.with(context).load(url).into(homeItem1Binding.homeItemIms);
    }
}
