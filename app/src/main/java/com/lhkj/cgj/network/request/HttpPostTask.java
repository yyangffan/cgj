package com.lhkj.cgj.network.request;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lhkj.cgj.base.network.HttpPost;
import com.lhkj.cgj.network.response.HttpResponse;
import com.lhkj.cgj.utils.UnicodeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyk on 2017/3/27.
 * function:post请求的方法
 */
public class HttpPostTask extends HttpPost<HttpResponse> {

    private String url;
    private Map<String, String> params;
    private int code;
    private Class response;

    /**
     * post请求的构造方法
     *
     * @param url      请求的地址
     * @param params   请求的参数
     * @param code     请求的识别码
     * @param response 请求返回的response
     */
    public HttpPostTask(String url, Map<String, String> params, int code, Class response) {
        this.url = url;
        this.params = params;
        this.code = code;
        this.response = response;
    }

    /**
     * post请求的构造方法
     *
     * @param url       请求的地址
     * @param paramJson 请求的Json参数
     * @param code      请求的识别码
     * @param response  请求返回的response
     */
    public HttpPostTask(String url, String paramJson, int code, Class response) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("param", paramJson);
        this.url = url;
        this.params = params;
        this.code = code;
        this.response = response;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Map<String, String> getBody() {
        return params;
    }


    @Override
    protected Object parse(String responseBody) {
        responseBody = UnicodeUtil.unicodeToString(responseBody);
        Log.i("HttpTask", "response:" + responseBody);
        Gson gson = new Gson();
        try {
            return gson.fromJson(responseBody, response);
        } catch (JsonSyntaxException e) {
            return gson.fromJson(responseBody, HttpResponse.class);
        }
    }

    @Override
    protected int identifier() {
        return code;
    }

}
