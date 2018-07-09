package com.lhkj.cgj.lock;

import android.content.Context;
import android.widget.Toast;

import com.lhkj.cgj.databinding.ActivityBindBinding;
import com.lhkj.cgj.databinding.ActivityDoubtBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.DoubtReponse;
import com.lhkj.cgj.network.response.LllInfoResponse;
import com.lhkj.cgj.ui.mine.LllInfoActivity;

import java.util.HashMap;

/**
 * 创建日期:2017/10/12 on 16:23
 * 描述:
 * 作者:郭士超
 * QQ:1169380200
 */

public class DoubtLock {

    private Context mContext;
    private ActivityDoubtBinding mBinding;

    private String info;

    public DoubtLock() {

    }

    public DoubtLock(Context context, ActivityDoubtBinding binding) {
        this.mContext = context;
        this.mBinding = binding;

        getHttpData();
    }

    private void getHttpData() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(DoubtReponse.class, RunTime.operation.getMine().DOUBT, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                DoubtReponse result = (DoubtReponse) data;
                switch (result.getResultcode()) {
                    case "200":
                        info = result.info;
                        mBinding.tvDoubt.setText(info);
                        break;
                    default:
                        Toast.makeText(mContext, "查询失败，请重试", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
