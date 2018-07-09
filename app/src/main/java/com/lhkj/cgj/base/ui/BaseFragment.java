package com.lhkj.cgj.base.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.lhkj.cgj.base.network.HttpAbstractTask;
import com.lhkj.cgj.base.network.HttpTask;


/**
 * Fragment 基础类
 * Created by liyk
 */

public class BaseFragment extends Fragment implements HttpAbstractTask.OnResponseCallback {
    private Dialog mDialog;

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDialog = onCreateProgressDialog(context);
    }

    /**
     * 创建进度条对话框
     */
    protected Dialog onCreateProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("正在努力加载......");
        return dialog;
    }

    /**
     * 显示进度条对话框
     */
    public void showProgress() {
        if (mDialog == null) {
            return;
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

    /**
     * 显示一个Toast
     *
     * @param toast toast内容
     */
    protected void showToast(String toast) {
        Context context = getContext();
        if (context != null) {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 显示一个长时间的Toast
     *
     * @param toast toast内容
     */
    protected void showLongToast(String toast) {
        Context context = getContext();
        if (context != null) {
            Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 执行task
     *
     * @param task HttpGet,HttpPost,HttpUpload的子类
     */
    protected void executeTask(HttpTask<?> task) {
        if (task == null) {
            return;
        }
        if (!task.hasCallback()) {
            task.setOnResponseCallback(this);
        }
        task.execute();
    }

    /**
     * 立即执行task
     *
     * @param task HttpGet,HttpPost,HttpUpload的子类
     */
    protected void executeTaskNow(HttpTask<?> task) {
        if (task == null) {
            return;
        }
        if (!task.hasCallback()) {
            task.setOnResponseCallback(this);
        }
        task.executeNow();
    }

    @Override
    public void onResponse(int identifier, Object response) {

    }
}
