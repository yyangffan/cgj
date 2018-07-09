package com.lhkj.cgj.ui.station;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ItemGasStationBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.BindingLock;
import com.lhkj.cgj.network.response.BindingOilReponse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 创建日期:2017/9/27 on 15:09
 * 描述:绑定站点
 * 作者:郭士超
 * QQ:1169380200
 */

public class GasStationAdapter extends RecyclerView.Adapter<GasStationAdapter.BindingHolder> {

    private Context mContext;
    private ArrayList<BindingLock.GasStationItem> mDatas;

    public GasStationAdapter(Context context, ArrayList<BindingLock.GasStationItem> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemGasStationBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_gas_station,
                viewGroup,
                false);

        BindingHolder holder = new BindingHolder(binding.getRoot(), mContext);
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        BindingLock.GasStationItem data = mDatas.get(position);
        holder.getBinding().setVariable(BR.gasStationItem, data);
        holder.getBinding().executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setAdapter(ArrayList<BindingLock.GasStationItem> datas) {
        this.mDatas = datas;
        this.notifyDataSetChanged();
    }


    public static class BindingHolder extends RecyclerView.ViewHolder {

        private ItemGasStationBinding mBinding;
        private Context mContext;

        public BindingHolder(View itemView, Context context) {
            super(itemView);
            this.mContext = context;
        }

        public ItemGasStationBinding getBinding() {
            return mBinding;
        }

        public void setBinding(ItemGasStationBinding binding) {
            this.mBinding = binding;

            binding.station.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RunTime.setData(RunTime.STATION_ID, mBinding.getGasStationItem().adminId);
                    ((BaseActivity) mContext).startActivity(StationInfoActivity.class);
                }
            });
            binding.tvBinding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bindingStation();
                }
            });
        }

        private void bindingStation() {
            HashMap hashMap = new HashMap();
            hashMap.put("user_id", User.getUser().userId);
            hashMap.put("oil_id", mBinding.getGasStationItem().adminId);
            RunTime.operation.tryPostRefresh(BindingOilReponse.class, RunTime.operation.getHome().BIND, hashMap, new Operation.Listener() {
                @Override
                public void tryReturn(int id, Object data) {
                    BindingOilReponse result = (BindingOilReponse) data;
                    if (result.getResultcode().equals("200")) {
                        Toast.makeText(mContext, "绑定成功", Toast.LENGTH_SHORT).show();
                        User.getUser().userOilName = mBinding.getGasStationItem().name;
                        User.getUser().userOilId = mBinding.getGasStationItem().adminId;
                        ((Activity) mContext).finish();
                    } else if (result.getResultcode().equals("301")) {
                        Toast.makeText(mContext, "未间隔三个月不能换绑", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "绑定失败，稍后重试", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}

