package com.lhkj.cgj.ui.mine;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.MyspeakItemBinding;
import com.lhkj.cgj.lock.MySpeakLock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 浩琦 on 2017/6/22.
 */

public class MySpeakAdapter extends BaseTopAdapter {
    private ArrayList<MySpeakLock.MySpeakItem> list;
    private Context context;
    public MySpeakAdapter(Context context, List list, int layoutId, int variableId) {
        super(context, list, layoutId, variableId);
        this.list=(ArrayList) list;
        this.context=context;
    }

    @Override
    protected void subClassTask(ViewDataBinding binding, int position) {
        super.subClassTask(binding, position);
        MyspeakItemBinding myspeakItemBinding=(MyspeakItemBinding)binding;
        Glide.with(context).load(list.get(position).speakUrl).into(myspeakItemBinding.speakItemIm);
    }
}
