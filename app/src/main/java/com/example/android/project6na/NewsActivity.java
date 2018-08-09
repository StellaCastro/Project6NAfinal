package com.example.android.project6na;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsClass>> {
    public static final String LOG_TAG = NewsActivity.class.getName();
    //our URL
    private static final String ENVIRONMENTAL_NEWS_URL =
            "https://content.guardianapis.com/search?q=ecology&api-key=e2f3ffb7-87fe-439a-983f-83c82a9783ea";
   //declaring the different variables that we need in this activity
    private static final int NEWS_LOADER_ID = 1;
    private NewArrayAdapter mAdapter;
    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // here we are expanding the list view and inserting the different items inside using the custom apadter that we created
        ListView newsListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);
        mAdapter = new NewArrayAdapter(this, new ArrayList<NewsClass>());
        newsListView.setAdapter(mAdapter);

        //here we are creating a click listener to know when the user click on a list item
        // then it takes the url from that items and opens it on the browser
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsClass currentNews = mAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });

        // Here we are checking the state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // here we are telling the loader what to do if there is internet connection
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            //here we telling the app what to do when there is a connection issue to the internet
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);


        }
    }


    @Override
    public Loader<List<NewsClass>> onCreateLoader(int i, Bundle bundle) {

        // Create a news loader for the given URL
        return new newsLoader(this, ENVIRONMENTAL_NEWS_URL);

    }
    // here we are telling what to do when the load is finish
    @Override
    public void onLoadFinished(Loader<List<NewsClass>> loader, List<NewsClass> newsClasses) {
        // Hide loading indicator
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_news);
        // Clear the adapter of previous news data
        mAdapter.clear();

        if (newsClasses != null && !newsClasses.isEmpty()) {
            mAdapter.addAll(newsClasses);

        }

    }
    // Resetting the loader
    @Override
    public void onLoaderReset(Loader<List<NewsClass>> loader) {
        mAdapter.clear();

    }


}


