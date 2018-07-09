package com.lhkj.cgj.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * 创建日期:2017/10/17 on 10:19
 * 描述:
 * 作者:郭士超
 * QQ:1169380200
 */

public class JPushKeyReponse {
    /**
     * 0 : jiguang
     * key : value
     */

    @SerializedName("0")
    private String _$0;
    private String key;

    public String get_$0() {
        return _$0;
    }

    public void set_$0(String _$0) {
        this._$0 = _$0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
