package com.example.android.project6na;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;

public class newsLoader extends AsyncTaskLoader {
     // Tag for log messages
    private static final String LOG_TAG = newsLoader.class.getName();

    private String mUrl;
    public newsLoader(Context context, String url) {

        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsClass> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news.
        List<NewsClass> newsList = QueryUtils.fetchEcoNewsData(mUrl);
        return newsList;
    }
}
