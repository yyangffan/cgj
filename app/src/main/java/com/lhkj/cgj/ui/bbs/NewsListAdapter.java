package com.lhkj.cgj.ui.bbs;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.NewsItemBinding;
import com.lhkj.cgj.lock.NewsLock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 浩琦 on 2017/6/23.
 */

public class NewsListAdapter extends BaseTopAdapter {
    private ArrayList<NewsLock.NewsItem> list;
    private Context context;

    public NewsListAdapter(Context context, List list, int layoutId, int variableId) {
        super(context, list, layoutId, variableId);
        this.list = (ArrayList) list;
        this.context = context;
    }

    @Override
    protected void subClassTask(ViewDataBinding binding, int position) {
        super.subClassTask(binding, position);
        NewsItemBinding itemBinding = (NewsItemBinding) binding;
        Glide.with(context).load(list.get(position).imsUrl).into(itemBinding.homeItemIms);

    }
}
