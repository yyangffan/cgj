package com.lhkj.cgj.ui.station;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityBindingBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.BindingLock;

import java.util.ArrayList;

/**
 * 创建日期:2017/9/27 on 14:54
 * 描述:绑定加油站
 * 作者:郭士超
 * QQ:1169380200
 */

public class BindingActivity extends BaseActivity {
    private ActivityBindingBinding binding;
    private BindingLock bindLock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_binding);
        bindLock = new BindingLock(this, binding);
        binding.setBindingLock(bindLock);
        binding.include4.setAppBarLock(new AppBarLock(this, R.string.bind, R.mipmap.icon_back, 0, true, false));
    }

    public void searchOil(View view) {
        if (bindLock.mData != null && bindLock.bindData.selectBind != null && !bindLock.bindData.selectBind.isEmpty()) {
            ArrayList<BindingLock.GasStationItem> d = new ArrayList<BindingLock.GasStationItem>();
            for (BindingLock.GasStationItem data : bindLock.mData) {
                if (data.name != null && data.name.indexOf(bindLock.bindData.selectBind) != -1) {
                    d.add(new BindingLock.GasStationItem(data.adminId, data.name));
                }
            }
            bindLock.mAdapter.setAdapter(d);
            if(d.isEmpty()) {
                Toast.makeText(this, "未找到指定加油站", Toast.LENGTH_SHORT).show();
            }
        } else {
            bindLock.mAdapter.setAdapter(bindLock.mData);
            Toast.makeText(this, "请填写加油站名称", Toast.LENGTH_SHORT).show();
        }
    }

    public ActivityBindingBinding getBinding() {
        return binding;
    }
}
