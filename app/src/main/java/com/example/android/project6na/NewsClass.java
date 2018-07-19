package com.example.android.project6na;

public class NewsClass {
    private String mTittle;
    private String mTopic;
    private String mUrl;

    public NewsClass (String tittle, String topic, String url){
        mTittle = tittle;
        mTopic = topic;
        mUrl = url;
    }



    public String getTittle() {
        return mTittle;
    }

    public String getTopic() {
        return mTopic;
    }



    public String getUrl() {
        return mUrl;
    }
}
