package com.lhkj.cgj.network.request;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/2/5.
 */

public class JsonForm<T> {
    public static <T> T formJson(String json, Class<T> tClass) {
        return GsonUtil.getGson().fromJson(json, tClass);
    }
}
