package com.lhkj.cgj.ui.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ItemHomeOilBinding;
import com.lhkj.cgj.lock.HomeLock;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/8/10.
 */

public class HomeOilAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<HomeLock.HomeOilItem> oils;

    public HomeOilAdapter(Context context, ArrayList<HomeLock.HomeOilItem> oils) {
        this.context = context;
        this.oils = oils;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHomeOilBinding homeOilBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_home_oil,
                parent,
                false);
        return new Type(homeOilBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHomeOilBinding homeOilBinding = DataBindingUtil.findBinding(holder.itemView);
        homeOilBinding.setHomeOilItem(oils.get(position));
    }

    @Override
    public int getItemCount() {
        return oils.size();
    }

    public class Type extends RecyclerView.ViewHolder {

        public Type(View itemView) {
            super(itemView);
        }
    }
}
