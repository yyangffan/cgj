package com.lhkj.cgj.network.request;

import com.google.gson.Gson;
import com.lhkj.cgj.base.network.HttpGet;
import com.lhkj.cgj.network.response.HttpResponse;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by liyk on 2017/3/27.
 * function:get请求的方法体
 */

public class HttpGetTask extends HttpGet<HttpResponse> {

    int code;
    String url;
    Map<String, String> params;
    Class aClass;


    /**
     * htt
     *
     * @param url
     * @param params
     * @param code
     * @param LoginResponse
     */
    public HttpGetTask(String url, Map<String, String> params, int code, Class LoginResponse) {
        this.url = url;
        this.params = params;
        this.code = code;
        this.aClass = LoginResponse;
    }


    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getQueryString() {
        StringBuffer stringBuffer = new StringBuffer();
        Set set = params.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry<String, String> entry1 = (Map.Entry<String, String>) i.next();
            stringBuffer.append(entry1.getKey() + "=" + params.get(entry1.getKey()) + "&");
        }
        return stringBuffer.toString();
    }

    @Override
    protected Object parse(String responseBody) {
        Gson gson = new Gson();
        return gson.fromJson(responseBody, aClass);
    }

    @Override
    public void executeNow() {
        super.executeNow();
    }

    @Override
    protected int identifier() {
        return code;
    }


}
