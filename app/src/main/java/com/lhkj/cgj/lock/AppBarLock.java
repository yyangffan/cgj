package com.lhkj.cgj.lock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.entity.News;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.ui.login.LoginActivity;
import com.lhkj.cgj.ui.main.MainActivity;
import com.lhkj.cgj.ui.mine.ShareUserActivity;
import com.lhkj.cgj.ui.shop.ShopExchangeActivity;
import com.lhkj.cgj.ui.station.BindingActivity;
import com.lhkj.cgj.utils.PopManager;
import com.xys.libzxing.zxing.activity.CaptureActivity;

/**
 * Created by 浩琦 on 2017/6/16.
 * 所有Bar
 */

public class AppBarLock {
    private Context context;
    private int leftType;
    private int rightType;
    public AppBarData barData;
    public static final int BACK = 1000;
    public static final int BIND = 1001;
    public static final int SHARE = 2000;
    public static final int LOGIN = 2001;
    public static final int MY_SHARE = 2002;
    public static final int OTHER = 9999;
    public static final int DUIHUANJILU = 110;

    private boolean isTitleCanClick = false;

    public AppBarLock(Context context, int title) {
        this.context = context;
        barData = new AppBarData(context.getResources().getString(title), context.getResources().getDrawable(R.mipmap.icon_back), null, null, null, true, false);
        leftType = BACK;
    }

    public AppBarLock(Context context, String title) {
        this.context = context;
        barData = new AppBarData(title, context.getResources().getDrawable(R.mipmap.icon_back), null, null, null, true, false);
        leftType = BACK;
    }

    public AppBarLock(Context context, int title, int imsLeft, int imsRight, boolean isLeft, boolean isRight) {
        this.context = context;
        if (imsLeft == 0) {
            barData = new AppBarData(context.getResources().getString(title), null, context.getResources().getDrawable(imsRight), null, null, isLeft, isRight);
        } else if (imsRight == 0) {
            barData = new AppBarData(context.getResources().getString(title), context.getResources().getDrawable(imsLeft), null, null, null, isLeft, isRight);
        } else {
            barData = new AppBarData(context.getResources().getString(title), context.getResources().getDrawable(imsLeft), context.getResources().getDrawable(imsRight), null, null, isLeft, isRight);
        }
        leftType = BACK;
    }

    public AppBarLock(Context context, int title, int titleLeft, int titleRight, boolean isLeft, boolean isRight, boolean over) {
        this.context = context;
        if (titleLeft == 0) {
            barData = new AppBarData(context.getResources().getString(title), null, null, null, context.getResources().getString(titleRight), isLeft, isRight);

        } else if (titleRight == 0) {
            barData = new AppBarData(context.getResources().getString(title), null, null, context.getResources().getString(titleLeft), null, isLeft, isRight);

        } else {
            barData = new AppBarData(context.getResources().getString(title), null, null, context.getResources().getString(titleLeft), context.getResources().getString(titleRight), isLeft, isRight);
        }
    }

    public AppBarLock(Context context, int title, int imsLeft, int titleRight) {
        this.context = context;
        barData = new AppBarData(context.getResources().getString(title), context.getResources().getDrawable(imsLeft), null, null, context.getResources().getString(titleRight), true, true);
        leftType = BACK;
    }

    public AppBarLock(Context context, String title, int imsLeft, int titleRight) {
        this.context = context;
        barData = new AppBarData(title, context.getResources().getDrawable(imsLeft), context.getResources().getDrawable(titleRight), null, null, true, true);
        leftType = BACK;
    }

    public AppBarLock setRight(int type) {
        this.rightType = type;
        return this;
    }

    public AppBarLock setLeft(int type) {
        this.leftType = type;
        return this;
    }

    public void leftClick() {
        switch (leftType) {
            case BACK:
                back();
                break;
            case BIND:
                // TODO: 2018/3/28 注释掉这里--因为将绑定转移到了title上
//                bind();
                break;
            case OTHER:
                //
                break;
        }
    }

    /*用来表示中间的Title是否能够点击  true-可以 false-不可以*/
    public void setTitleCanClick(boolean titleCanClick) {
        isTitleCanClick = titleCanClick;
    }

    /*点击中间title跳转 主要是为了进行加油站的绑定*/
    public void centerTitleClick() {
        if (isTitleCanClick) {
            bind();
        }
    }

    public void rightClick() {
        switch (rightType) {
            case SHARE:
                share();
                break;
            case LOGIN:
                login();
                break;
            case MY_SHARE:
                shareUser();
                break;
            case DUIHUANJILU:
                if (User.isLogin()) {
                    ((MainActivity) context).startActivity(ShopExchangeActivity.class);
                }
                break;
        }
    }

    /*11.18添加主页扫描二维码*/
    public void erweima() {
        ((MainActivity) context).startActivityForResult(new Intent(context, CaptureActivity.class), 200);
    }

    private void back() {
        ((BaseActivity) context).finish();
    }

    private void share() {
        if (User.getUser().isLogin()) {
            if (barData.title.equals("邀请有礼")) {
                new PopManager(context).showShare("Mobile/Onepage/index?user_id=", null, null);
            } else {
                News news = (News) RunTime.getRunTime(RunTime.NEWID);
                new PopManager(context).showShare("mobile/onepage/pinglun?id=", news.speakId, news.speakTitle);
            }
        } else {
            ((BaseActivity) context).startActivity(LoginActivity.class);
        }
    }

    private void shareUser() {
        ((BaseActivity) context).startActivity(ShareUserActivity.class);
    }

    private void bind() {
        if (User.isLogin()) {
            if (User.getUser().userOilName == null || User.getUser().userOilName.isEmpty()) {
                ((BaseActivity) context).startActivity(BindingActivity.class);
            } else {
//                showDialog();
                // 现在不提示直接清了
                ((BaseActivity) context).startActivity(BindingActivity.class);
            }
        } else {
            ((BaseActivity) context).startActivity(LoginActivity.class);
        }

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.success);
        builder.setMessage("换绑加油站积分清空！");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((BaseActivity) context).startActivity(BindingActivity.class);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void login() {
        ((BaseActivity) context).startActivity(LoginActivity.class);
    }

    public class AppBarData extends BaseObservable {
        public AppBarData(String title, Drawable imsLeft, Drawable imsRight, String titleLeft, String titleRight, boolean isLeft, boolean isRight) {
            this.title = title;
            this.imsLeft = imsLeft;
            this.imsRight = imsRight;
            this.isLeft = isLeft;
            this.isRight = isRight;
            this.titleLeft = titleLeft;
            this.titleRight = titleRight;
        }

        public String title;
        public String titleLeft;
        public String titleRight;
        public Drawable imsLeft;
        public Drawable imsRight;
        public boolean isLeft;
        public boolean isRight;
        public boolean isBig = true;/*用来设置标题的大小*/
    }
}
