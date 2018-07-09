package com.lhkj.cgj.base.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 14  * 通用的adapter
 * 15  * Created by yhq
 * 16
 */


public class BaseTopAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> list;
    private int layoutId;//单布局
    private int variableId;
    private ViewListener listener;

    public BaseTopAdapter(Context context, List<T> list, int layoutId, int variableId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
        this.variableId = variableId;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        if (variableId != 0) {
            binding.setVariable(variableId, list.get(position));
        }
        subClassTask(binding, position);
        return binding.getRoot();
    }

    protected void subClassTask(ViewDataBinding binding, int position) {
        if (listener != null) {
            listener.viewListener(binding, position);
        }
    }

    public void setListener(ViewListener listener) {
        this.listener = listener;
    }

    public interface ViewListener {
        void viewListener(ViewDataBinding binding, int position);
    }
}
