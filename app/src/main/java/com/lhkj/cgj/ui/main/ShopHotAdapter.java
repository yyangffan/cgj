package com.lhkj.cgj.ui.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ItemShopHotBinding;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.ShopLock;
import com.lhkj.cgj.ui.shop.ShopDetailsActivity;

import java.util.ArrayList;

/**
 * 创建日期：2017/10/19 on 13:46
 * 描述：
 * 作者：郭士超
 * QQ：1169380200
 */

public class ShopHotAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<ShopLock.ShopHotItem> hots;

    public ShopHotAdapter(Context context, ArrayList<ShopLock.ShopHotItem> hots) {
        this.context = context;
        this.hots = hots;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemShopHotBinding mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_shop_hot,
                parent,
                false);
        return new ShopHotAdapter.Type(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemShopHotBinding mBinding = DataBindingUtil.findBinding(holder.itemView);
        mBinding.setShopHotItem(hots.get(position));

        Glide.with(context).load(hots.get(position).url).into(mBinding.listIm);
    }

    @Override
    public int getItemCount() {
        return hots.size();
    }

    public class Type extends RecyclerView.ViewHolder {

        public Type(View itemView, final ItemShopHotBinding binding) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (User.getUser().userOilId == null || User.getUser().userOilId.equals("")) {
                        return;
                    }

                    RunTime.setData(RunTime.SHOPID, binding.getShopHotItem().id);
                    RunTime.setData(RunTime.SHOP_TYPE, 0);
                    ((MainActivity) context).startActivity(ShopDetailsActivity.class);
                }
            });
        }
    }

}
