package com.example.android.project6na;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


    public final class QueryUtils {

        /** Tag for the log messages */
        private static final String LOG_TAG = QueryUtils.class.getSimpleName();
        private QueryUtils() {
        }


        public static List<NewsClass> fetchEcoNewsData(String requestUrl) {

            // Create URL object
            URL newsUrl = createUrl(requestUrl);
            String jsonResponse = null;
            try {
                jsonResponse = makeHttpRequest(newsUrl);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }

            // Here we start extracting the data
            List<NewsClass> newsList = extractEcoNews(jsonResponse);
            return newsList;
        }
    private static List<NewsClass> extractEcoNews(String jsonResponse) {
        // Checking if JSON string is empty or null.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<NewsClass> newsList = new ArrayList<>();
        try {
            JSONObject rootJsonResponse = new JSONObject(jsonResponse);
            JSONObject rootJSONResponseResult = rootJsonResponse.getJSONObject("response");
            JSONArray newsArray = rootJSONResponseResult.getJSONArray("results");


            // here we start looping inside of the array
            for (int i = 0; i < newsArray.length(); i++) {

                // Get a single news at position i within the list of news
                // grabbing the title, topic and url from each element in the JSONArray
                JSONObject currentEcoNews = newsArray.getJSONObject(i);

                String news_title = currentEcoNews.getString("webTitle");
                String news_topic = currentEcoNews.getString("sectionName");
                String publishDate = currentEcoNews.getString("webPublicationDate");
                String news_url = currentEcoNews.getString("webUrl");
                JSONArray tagsArray = currentEcoNews.getJSONArray("tags");
                JSONObject author = tagsArray.getJSONObject(0);
              String news_author = author.getString("webTitle");



                // here we are creating a new object with the title and topic,
                // and url from the JSON response.
                NewsClass newsResponse = new NewsClass(news_title, news_topic, news_url, publishDate, news_author);
                newsList.add(newsResponse);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of news
        return newsList;
    }
    //here we are making the Http Request
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // Checking for null
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        //here we create the connection to the http
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 );
            urlConnection.setConnectTimeout(15000 );
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    // here we are creating the string builder preferable for our case
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

   //here we are telling the app what to do if the url has and issue
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

}
