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
        // Get the {@link AndroidFlavor} object located at this position in the list
        NewsClass currentNews = getItem(position);

        // Find the TextView with view ID title
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        // Display the title of the current news in that TextView
        titleView.setText(currentNews.getTittle());

        // Find the TextView with view ID title
        TextView topicView = (TextView) listItemView.findViewById(R.id.topic);
        // Display the topic of the current news in that TextView
        titleView.setText(currentNews.getTopic());



        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

}
