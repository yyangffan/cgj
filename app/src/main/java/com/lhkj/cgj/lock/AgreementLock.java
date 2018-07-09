package com.lhkj.cgj.lock;

import android.content.Context;

import com.lhkj.cgj.databinding.ActivityAgreementBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.network.response.ProtocolReponse;

import java.util.HashMap;

/**
 * Created by user on 2017/11/23.
 * 用户协议
 */

public class AgreementLock {
    ActivityAgreementBinding mBinding;
    Context context;

    public AgreementLock(ActivityAgreementBinding binding, Context context) {
        mBinding = binding;
        context = context;
        initData();

    }

    private void initData() {
        RunTime.operation.tryPostRefresh(ProtocolReponse.class, RunTime.operation.getMine().PROTOCOL, new HashMap(), new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                ProtocolReponse protocolReponse = (ProtocolReponse) data;
                mBinding.agreementTvContent.setText(protocolReponse.info.content);
            }
        });//
    }
}
