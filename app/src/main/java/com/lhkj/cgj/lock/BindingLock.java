package com.lhkj.cgj.lock;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v7.widget.LinearLayoutManager;

import com.lhkj.cgj.databinding.ActivityBindingBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.network.response.GasStationResponse;
import com.lhkj.cgj.ui.station.GasStationAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 创建日期:2017/9/27 on 14:56
 * 描述:
 * 作者:郭士超
 * QQ:1169380200
 */

public class BindingLock {

    public GasStationAdapter mAdapter;
    public ArrayList<GasStationItem> mData;

    private Context context;
    private ActivityBindingBinding mBinding;

    public BindData bindData;

    public BindingLock(Context context, ActivityBindingBinding binding) {
        this.mBinding = binding;
        this.context = context;
        bindData = new BindData();
        mData = new ArrayList();

        LinearLayoutManager ms = new LinearLayoutManager(context);
        mBinding.rvStation.setLayoutManager(ms);
        mAdapter = new GasStationAdapter(context, mData);
        mBinding.rvStation.setAdapter(mAdapter);

        getData();
    }


    private void getData() {
        mData.clear();
        HashMap hashMap = new HashMap();
        RunTime.operation.tryPostRefresh(GasStationResponse.class, RunTime.operation.getMine().GAS_LIST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                GasStationResponse gasData = (GasStationResponse) data;

                for (GasStationResponse.Info info : gasData.info) {
                    GasStationItem gasStationItem = new GasStationItem(info.admin_id, info.name);
                    mData.add(gasStationItem);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public static class GasStationItem {

        public GasStationItem(String adminId, String name) {
            this.adminId = adminId;
            this.name = name;
        }

        public String adminId;
        public String name;

    }

    public class BindData extends BaseObservable {
        public String selectBind;
    }

}
