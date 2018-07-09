package com.lhkj.cgj.entity;

/**
 * Created by 浩琦 on 2017/6/23.
 */

public class News {

    public String speakId;
    public String zan;
    public boolean isZan;
    public String speakUrl;
    public String speakTitle;
    public String speakText;
    public boolean speakReturn;

    public News(String speakId, String speakUrl, String speakTitle, String speakText, boolean speakReturn, String zan) {
        this.speakId = speakId;
        this.speakUrl = speakUrl;
        this.speakTitle = speakTitle;
        this.speakText = speakText;
        this.speakReturn = speakReturn;
        this.zan = zan;
        if (this.zan == null){
            this.zan = "0";
        }
    }
}
