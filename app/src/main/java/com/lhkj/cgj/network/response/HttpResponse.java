package com.lhkj.cgj.network.response;

/**
 * Created by liyk on 2017/3/27.
 * function:
 */

public class HttpResponse<T> {

    private String code;
    private String message;
    private String success;


    private String token_statu = "0";

    public String getTokenStatu() {
        return token_statu;
    }

    public void setTokenStatu(String token_statu) {
        this.token_statu = token_statu;
    }



//    private  T info;
//    public T getInfo() {
//        return info;
//    }
//
//    public void setInfo(T info) {
//        this.info = info;
//    }


    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public String getResultcode() {
        return code;
    }

    public void setResultcode(String resultcode) {
        this.code = resultcode;
    }

    public String getResultmsg() {
        return message;
    }

    public void setResultmsg(String resultmsg) {
        this.message = resultmsg;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
