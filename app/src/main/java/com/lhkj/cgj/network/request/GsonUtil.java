package com.lhkj.cgj.network.request;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/5/24.
 */

public class GsonUtil {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
