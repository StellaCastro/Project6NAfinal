package com.example.android.project6na;

public class NewsClass {
    private String mTitle;
    private String mTopic;
    private String mUrl;
    private String mDate;
    private String mAuthor;


    public NewsClass (String title, String topic, String url, String date, String author){
        mTitle = title;
        mTopic = topic;
        mUrl = url;
        mDate = date;
        mAuthor = author;

    }


    public String getTitle() {
        return mTitle;
    }

    public String getTopic() {
        return mTopic;
    }

    public String getDate (){return mDate;}

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }


}
