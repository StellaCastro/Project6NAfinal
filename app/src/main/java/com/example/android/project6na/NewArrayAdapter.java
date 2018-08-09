package com.example.android.project6na;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;



public class NewArrayAdapter extends ArrayAdapter<NewsClass> {
    private static final String DATE_DIVIDER = "T";

    public NewArrayAdapter(Activity context, ArrayList<NewsClass> newsList){
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, newsList);

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }
        NewsClass currentNews = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentNews.getTitle());

        TextView topicView = (TextView) listItemView.findViewById(R.id.topic);
        topicView.setText(currentNews.getTopic());

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);

        authorView.setText("By: " + currentNews.getAuthor());
        // here we are removing the time from the date and placing it in the text view
        String datePublished = currentNews.getDate();
        String date;
        String[] parts = datePublished.split(DATE_DIVIDER);
        date = "Date: " + parts[0];
       TextView dateView = (TextView) listItemView.findViewById(R.id.date);
       dateView.setText(date);

        return listItemView;
    }



}
