package com.lhkj.cgj.utils;

import com.lhkj.cgj.base.network.HttpAbstractTask;
import com.lhkj.cgj.base.network.HttpTask;

/**
 * Created by 浩琦 on 2017/6/20.
 */

public class HttpTaskSubmit {
    /**
     * 执行task
     *
     * @param task HttpGet,HttpPost,HttpUpload的子类
     */
    public static void executeTask(HttpTask<?> task, HttpAbstractTask.OnResponseCallback callback) {
        if (task == null) {
            return;
        }
        if (!task.hasCallback()) {
            task.setOnResponseCallback(callback);
        }
        task.execute();
    }

    /**
     * 立即执行task
     *
     * @param task HttpGet,HttpPost,HttpUpload的子类
     */
    public static void executeTaskNow(HttpTask<?> task, HttpAbstractTask.OnResponseCallback callback) {
        if (task == null) {
            return;
        }
        if (!task.hasCallback()) {
            task.setOnResponseCallback(callback);
        }
        task.executeNow();
    }
}
