package com.lhkj.cgj.utils;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.wxapi.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by 浩琦 on 2017/6/20.
 * popwindow显示
 */

public class PopManager {
    private PopupWindow window;
    private int wei;
    private ViewDataBinding dataBinding;
    private Context context;
    private boolean isOver;
    private MissLisenter lisenter;

    public PopManager(Context context) {
        this.context = context;
    }

    public ViewDataBinding showTransparentPop(View v, int resid) {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), resid, null, false);
        windowAttribute(dataBinding.getRoot(), true);
        window.showAtLocation(v, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        setAhpla();
        return dataBinding;
    }

    public ViewDataBinding showPop(View v, int resid) {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), resid, null, false);
        windowAttribute(dataBinding.getRoot(), false);
        window.showAtLocation(v, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        setAhpla();
        return dataBinding;
    }

    public ViewDataBinding showAsDrowPop(View v, int resid) {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), resid, null, false);
        windowAttribute(dataBinding.getRoot(), false);
        window.showAsDropDown(v, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        setAhpla();
        return dataBinding;
    }

    public void disPop(View v, final boolean isover) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (window != null) {
                    window.dismiss();
                    window = null;
                }
                isOver = isover;
                if (isover) {
                    ((BaseActivity) context).finish();
                    isOver = false;
                }
            }
        });
    }

    public void stop() {
        if (window != null) {
            window.dismiss();
            window = null;
        }
    }

    public void setMissLisenter(MissLisenter lisenter) {
        this.lisenter = lisenter;
    }

    private void windowAttribute(View popupView, boolean isT) {
        //类型是TYPE_TOAST，像一个普通的Android Toast一样。这样就不需要申请悬浮窗权限了。
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);
        //初始化后不首先获得窗口焦点。不妨碍设备上其他部件的点击、触摸事件。
//        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height =WindowManager.LayoutParams.WRAP_CONTENT;
        window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        window.setAnimationStyle(R.style.popup_window_anim);
        if (isT) {
            window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.apl)));
        } else {
            window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.white)));
        }
//        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
    }

    private void setAhpla() {
        WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow().getAttributes();
        lp.alpha = 0.6f; // 0.0-1.0
        ((BaseActivity) context).getWindow().setAttributes(lp);
        ((BaseActivity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp1 = ((BaseActivity) context).getWindow().getAttributes();
                lp1.alpha = 1.0f; // 0.0-1.0
                ((BaseActivity) context).getWindow().setAttributes(lp1);
                if (isOver) {
                    ((BaseActivity) context).finish();
                }
                if (lisenter != null) {
                    lisenter.miss();
                }
            }
        });
    }

    public interface MissLisenter {
        void miss();
    }

    public void showShare(String url, String newId, String title) {
        String shareUrl = "";
        if (newId == null) {
            shareUrl = RunTime.BASEURL + url + User.getUser().userId;
        } else {/*分享的是文章*/
            shareUrl = RunTime.BASEURL + url + newId;
        }

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setSilent(true);//隐藏编辑页面
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        if (newId == null) {
            oks.setTitle(context.getResources().getString(R.string.app_name));
        } else {
            oks.setTitle(title);
        }
//        oks.setTitle(context.getResources().getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(shareUrl);  // 分享的网站名
        // text是分享文本，所有平台都需要这个字段
        if (newId == null) {
            oks.setText("我发现一个好玩的应用——油品惠");  // 分享的文字
        } else {
            oks.setText("  ");  // 分享的文字
        }
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/yph/logo.png");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        if (newId == null) {
            oks.setComment("我发现一个好玩的应用——油品惠");
        } else {
            oks.setComment("  ");
        }
//        oks.setComment("我发现一个好玩的应用——油品惠");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getResources().getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareUrl);
        // 启动分享GUI
        oks.show(context);
    }
}
