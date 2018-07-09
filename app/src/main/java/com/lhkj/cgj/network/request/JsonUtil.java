package com.lhkj.cgj.network.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/1/23.
 */

public class JsonUtil {
    public static boolean isJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject = null;
            return true;
        } catch (JSONException e) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                jsonArray = null;
                return true;
            } catch (JSONException e1) {
                return false;
            }
        }
    }
}
