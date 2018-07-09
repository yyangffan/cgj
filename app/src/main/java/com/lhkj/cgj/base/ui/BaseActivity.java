package com.lhkj.cgj.base.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lhkj.cgj.base.network.HttpAbstractTask;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.utils.ActivityCollector;
import com.umeng.analytics.MobclickAgent;

import java.util.List;


/**
 * Activity基础类
 * Created by liyk on 2016/8/16.
 */

public class BaseActivity extends AppCompatActivity implements HttpAbstractTask.OnResponseCallback {

    private Dialog mDialog;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        ActivityCollector.addActivity(this);
//        Operation operation;
//        if (RunTime.operation == null) {
//            operation = new Operation();
//            RunTime.operation = operation;
//        }
//        if (RunTime.appContext == null) {
//            RunTime.appContext=getApplicationContext();
//        }
//
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        Operation operation;
        if (RunTime.operation == null) {
            operation = new Operation();
            RunTime.operation = operation;
        }
        if (RunTime.appContext == null) {
            RunTime.appContext = getApplicationContext();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        hideProgress();
    }

    /**
     * 创建进度条对话框
     */
    protected Dialog onCreateProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("正在努力加载......");
        return dialog;
    }

    /**
     * 显示进度条对话框
     */
    public void showProgress() {
        if (mDialog == null) {
            mDialog = onCreateProgressDialog();
            if (mDialog == null) {
                return;
            }
        }
        mDialog.show();
    }

    /**
     * 隐藏进度条对话框
     */
    public void hideProgress() {
        if (isProgressing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 进度条是否在显示
     */
    public boolean isProgressing() {
        return (mDialog != null && mDialog.isShowing());
    }

    @Override
    public void onBackPressed() {
        if (isProgressing()) {
            hideProgress();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 显示一个Toast
     *
     * @param toast toast内容
     */
    protected void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长时间的Toast
     *
     * @param toast toast内容
     */
    protected void showLongToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResponse(int identifier, Object responseBody) {

    }

    public void displayFragment(List<Fragment> fragments, Fragment fragment, FragmentManager fm,
                                int containerViewId) {
        FragmentTransaction ft = fm.beginTransaction();
        hideFragments(fragments, ft);
        if (fragment.isAdded()) {
            ft.show(fragment);
            ft.commit();
        } else {
            ft.add(containerViewId, fragment);
            ft.show(fragment);
            ft.commit();
        }
    }

    public void hideFragments(List<Fragment> fragments, FragmentTransaction ft) {
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) != null) {
                ft.hide(fragments.get(i));
            }
        }
    }

    public void startActivity(Class<?> name) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), name);
        startActivity(intent);
    }

    public void startActivityForm(Class<?> name, int code) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), name);
        startActivityForResult(intent, code);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
