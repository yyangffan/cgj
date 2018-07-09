package com.lhkj.cgj.base.network;

import android.util.Log;

import okhttp3.Request;

/**
 * GET 请求
 * Created by Lianxw on 2016/8/16.
 */

public abstract class HttpGet<T> extends HttpTask<T> {

    @Override
    protected Request createRequest() {
        String url = createUrl();
        Log.i("GET", "url:" + url);
        return new Request.Builder().url(url).build();
    }
}
