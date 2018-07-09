package com.lhkj.cgj.ui.mine;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.LllgameItemBinding;
import com.lhkj.cgj.lock.LllGameLock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 浩琦 on 2017/6/22.
 */

public class LllGameAdapter extends BaseTopAdapter {
    private Context context;
    private ArrayList<LllGameLock.LllGameItem> list;
    public LllGameAdapter(Context context, List list, int layoutId, int variableId) {
        super(context, list, layoutId, variableId);
        this.context=context;
        this.list=(ArrayList<LllGameLock.LllGameItem>) list;
    }

    @Override
    protected void subClassTask(ViewDataBinding binding, int position) {
        super.subClassTask(binding, position);
        LllgameItemBinding lllgameItemBinding= (LllgameItemBinding)binding;
        Glide.with(context).load(list.get(position).gameimUrl).into(lllgameItemBinding.lllgameItemIm);
    }
}
