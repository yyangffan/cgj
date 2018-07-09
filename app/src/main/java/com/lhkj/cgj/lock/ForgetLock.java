package com.lhkj.cgj.lock;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ActivityForgetBinding;
import com.lhkj.cgj.databinding.PopToastBinding;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.utils.PopManager;
import com.lhkj.cgj.utils.ToolUtils;

/**
 * Created by 浩琦 on 2017/6/20.
 * 重置密码
 */

public class ForgetLock {
    private Context context;
    public ForgetData forgetData;
    private ActivityForgetBinding forgetBinding;
    private int timer = 30;

    public ForgetLock(Context context, ActivityForgetBinding forgetBinding) {
        this.context = context;
        this.forgetBinding = forgetBinding;
        forgetData = new ForgetData();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            timer--;
            if (timer <= 0) {
                forgetBinding.sendcode.setText(context.getResources().getString(R.string.sendcode));
                timer = 30;
            } else {
                forgetBinding.sendcode.setText("请稍后" + timer);
                sendTime(1000);
            }
        }
    };

    private void sendTime(long time) {
        handler.sendEmptyMessageDelayed(0, time);
    }

    public void sendCode() {
        if (ToolUtils.isMobileNO(forgetData.forgetPhone)) {
            if (timer == 30) {
                sendTime(1000);
                timer--;
                forgetBinding.sendcode.setText("请稍后" + timer);
                RunTime.operation.getMine().trySendForCode(forgetData.forgetPhone, new User.AuthorizationListener() {
                    @Override
                    public void authorization(boolean isOk) {

                    }
                });
            }
        } else {
            Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        }
    }

    public void next() {
        if (ToolUtils.isMobileNO(forgetData.forgetPhone) &&forgetData.forgetPwd!=null&& forgetData.forgetPwd.equals(forgetData.forgetRepwd)) {
            if (forgetData.forgetCode.equals("")) {
                Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!ToolUtils.isPwd(forgetData.forgetPwd)) {
                Toast.makeText(context, "密码格式错误，请重新输入", Toast.LENGTH_SHORT).show();
                return;
            }
            RunTime.operation.getMine().tryForget(forgetData.forgetPhone, forgetData.forgetCode, forgetData.forgetPwd, forgetData.forgetRepwd, new User.AuthorizationListener() {
                @Override
                public void authorization(boolean isOk) {
                    if (isOk){
                        PopManager popManager = new PopManager(context);
                        PopToastBinding toastBinding = (PopToastBinding) popManager.showPop(forgetBinding.linearLayout15, R.layout.pop_toast);
                        toastBinding.popIms.setImageResource(R.mipmap.success);
                        toastBinding.popText.setText(context.getResources().getString(R.string.forsuccess));
                        popManager.disPop(toastBinding.popEnter, true);
                    }else {
                        Toast.makeText(context, "用户不存在或验证码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(context, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        }
    }

    public class ForgetData {
        public String forgetPhone;
        public String forgetCode="";
        public String forgetPwd="";
        public String forgetRepwd="";
    }
}
