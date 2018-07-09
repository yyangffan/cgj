package com.lhkj.cgj.entity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lhkj.cgj.base.network.HttpAbstractTask;
import com.lhkj.cgj.network.request.HttpGetTask;
import com.lhkj.cgj.network.request.HttpPostTask;
import com.lhkj.cgj.network.response.HttpResponse;
import com.lhkj.cgj.ui.login.LoginActivity;
import com.lhkj.cgj.ui.main.MainActivity;
import com.lhkj.cgj.ui.main.MyApplication;
import com.lhkj.cgj.utils.HttpTaskSubmit;
import com.lhkj.cgj.utils.SharedPreferencesUtil;

import java.util.HashMap;

/**
 * 接口地址分类
 * Created by Super丶C on 2017/10/30.
 */

public class Operation {

    private Context mContext;

    private Shop shop;
    private NewsList newsList;
    private Home home;
    private Mine mine;

    public NewsList getNewsList() {
        if (newsList == null) {
            newsList = new NewsList();
        }
        return newsList;
    }

    public Shop getShop() {
        if (shop == null) {
            shop = new Shop();
        }
        return shop;
    }

    public Home getHome() {
        if (home == null) {
            home = new Home();
        }
        return home;
    }

    public Mine getMine() {
        if (mine == null) {
            mine = new Mine();
        }
        return mine;
    }

    public void tryGetRefresh(Class clazz, String listName, HashMap data, final Listener Listener) {
        if (listName == null || data == null) return;
        HttpGetTask httpGetTask = new HttpGetTask(listName, data, listName.hashCode(), clazz);
        HttpTaskSubmit.executeTask(httpGetTask, new HttpAbstractTask.OnResponseCallback() {
            @Override
            public void onResponse(int identifier, Object response) {
                Listener.tryReturn(identifier, response);
            }
        });
    }

    //屏蔽错误的请求
    public void tryPostRefresh(Class clazz, String listName, HashMap data, final Listener Listener) {
        if (listName == null || data == null) return;

        data.put("user_id", User.getUser().userId);
        data.put("token", SharedPreferencesUtil.getSharePreString(MyApplication.getApplication(), "token", ""));

        HttpPostTask httpPostTask = new HttpPostTask(listName, data, listName.hashCode(), clazz);
        HttpTaskSubmit.executeTask(httpPostTask, new HttpAbstractTask.OnResponseCallback() {
            @Override
            public void onResponse(int identifier, Object response) {
                checkToken((HttpResponse) response);

                if (((HttpResponse) response).getResultcode().equals("200")) {
                    Listener.tryReturn(200, response);
                } else {
                    Listener.tryReturn(100, response);
//                    if (((HttpResponse)response).getResultcode().equals("300")){
//                        Toast.makeText(RunTime.appContext,"网络错误", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    if (((HttpResponse) response).getResultmsg() != null && !((HttpResponse) response).getResultmsg().equals("")) {
                        Toast.makeText(RunTime.appContext, ((HttpResponse) response).getResultmsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void tryPostRefreshF(Class clazz, String listName, HashMap data, final Listener Listener) {
        if (listName == null || data == null) return;

        data.put("user_id", User.getUser().userId);
        data.put("token", SharedPreferencesUtil.getSharePreString(MyApplication.getApplication(), "token", ""));

        HttpPostTask httpPostTask = new HttpPostTask(listName, data, listName.hashCode(), clazz);
        HttpTaskSubmit.executeTask(httpPostTask, new HttpAbstractTask.OnResponseCallback() {
            @Override
            public void onResponse(int identifier, Object response) {
                checkToken((HttpResponse) response);

                if (((HttpResponse) response).getResultcode().equals("200")) {
                    Listener.tryReturn(200, response);
                } else {
                    Listener.tryReturn(Integer.parseInt(((HttpResponse) response).getResultcode()), response);
                }
            }
        });
    }

    private void checkToken(HttpResponse response) {
        if ("987".equals(response.getTokenStatu())) {
            // 下线
            Toast.makeText(RunTime.appContext, "账号在异地登入", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(MyApplication.getApplication(), MainActivity.class);
            MyApplication.getApplication().startActivity(intent);
            intent.setClass(MyApplication.getApplication(), LoginActivity.class);
            MyApplication.getApplication().startActivity(intent);
            User.getUser().clearUser();
            return;
        }
    }

    public interface Listener {
        void tryReturn(int id, Object data);
    }

    public interface noListener {
        void tryReturn(boolean isComm);
    }
}
