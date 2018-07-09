package com.lhkj.cgj.network.response;

import java.util.List;

/**
 * Created by user on 2018/4/12.
 */

public class TodayOilNewResponse extends HttpResponse{

    /**
     * code : 200
     * info : ["95  6.64  |  #96  123","92  5.2"]
     * token_statu : 789
     */

    private List<String> info;



    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }
}
