package com.lhkj.cgj.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.FragmentMineBinding;
import com.lhkj.cgj.lock.MineLock;

/********************************************************************
  @version: 1.0.0
  @description: 个人中心界面
  @author: user
  @time: 2018/3/22 11:36
  @变更历史: 2018/3/22进行了下方界面整体修改现保留着原界面为
  R.layout.fragment_minee现使用的的未修改后的
********************************************************************/

public class MineFragment extends Fragment {

    private MineLock mineLock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMineBinding mineBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        mineLock = new MineLock(getActivity(), mineBinding);
        mineBinding.setMineLock(mineLock);
        return mineBinding.getRoot();
    }


    public void flush() {
        if (mineLock != null) {
            mineLock.flushMine();
        }
    }

}
