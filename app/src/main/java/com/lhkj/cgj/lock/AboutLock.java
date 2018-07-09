package com.lhkj.cgj.lock;

import android.content.Context;
import android.databinding.BaseObservable;

import com.lhkj.cgj.databinding.ActivityAboutBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.network.response.ProtocolReponse;

import java.util.HashMap;

/**
 * 创建日期：2017/10/21 on 21:52
 * 描述：
 * 作者：郭士超
 * QQ：1169380200
 */

public class AboutLock {

    private Context context;
    private ActivityAboutBinding mBinding;

    public About about;

    public AboutLock(Context context, ActivityAboutBinding binding) {
        this.context = context;
        this.mBinding = binding;

        about = new About();

        RunTime.operation.tryPostRefresh(ProtocolReponse.class, RunTime.operation.getMine().ABOUT, new HashMap(), new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                ProtocolReponse protocolReponse = (ProtocolReponse) data;
                about.title = protocolReponse.info.title;
                about.content = protocolReponse.info.content;

                about.notifyChange();
            }
        });


    }

    public class About extends BaseObservable {

        public About() {
            this.title = "";
            this.content = "";
        }

        public String title = "";
        public String content = "";

    }

}
