package com.lhkj.cgj.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.FragmentHomeBinding;
import com.lhkj.cgj.lock.HomeLock;

/**
 * Created by 浩琦 on 2017/6/19.
 */

public class HomeFragment extends Fragment{
    public HomeLock homeLock;
    private FragmentHomeBinding mHomeBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        homeLock=new HomeLock(getActivity(), mHomeBinding);
        mHomeBinding.setHomeLock(homeLock);
        return mHomeBinding.getRoot();
    }



    public void flushHome(){
        homeLock.flush();
    }

    @Override
    public void onStart() {
        super.onStart();
        mHomeBinding.marqueeView.startFlipping();
        mHomeBinding.marqueeViewtwo.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHomeBinding.marqueeView.stopFlipping();
        mHomeBinding.marqueeViewtwo.stopFlipping();
    }
}
