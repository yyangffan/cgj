package com.lhkj.cgj.lock;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ActivitySetBinding;
import com.lhkj.cgj.databinding.PopDefTextBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.ProtocolReponse;
import com.lhkj.cgj.ui.other.AboutActivity;
import com.lhkj.cgj.ui.other.SetActivity;
import com.lhkj.cgj.utils.PopManager;

import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/20.
 * 设置页面
 */

public class SetLock {
    private Context context;
    private ActivitySetBinding setBinding;

    public SetLock(Context context, ActivitySetBinding setBinding) {
        this.context = context;
        this.setBinding = setBinding;

    }

    public void sendMsg() {
        if (setBinding.togButton.isChecked()) {
            setBinding.togButton.setChecked(true);
        } else {
            setBinding.togButton.setChecked(false);
        }
    }

    public void cleanCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
        Toast.makeText(context, "清理完成", Toast.LENGTH_SHORT).show();
    }

    public void about() {
        ((SetActivity) context).startActivity(AboutActivity.class);
    }

    public void edition() {
    }

    public void exit() {
        User.getUser().clearUser();
        ((SetActivity) context).finish();
    }
}
