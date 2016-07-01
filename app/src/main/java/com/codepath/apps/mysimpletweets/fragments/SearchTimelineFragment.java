package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kshah97 on 6/29/16.
 */
public class SearchTimelineFragment extends TweetsListFragment {

    private TwitterClient client;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }


    //send an API request to get the timeline JSON
    //fill the listview by creating the tweet objects from the json

    public void searchTweets(String query) {
        client = TwitterApplication.getRestClient();

        client.getSearchTweets(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Log.d("DEBUG", jsonObject.toString());


                //JSON coming in here
                //Deserialize JSON
                //create models and add them to the adapter
                //load the model data into the listview - we re going to need an adapter
                try {
                    addAll(Tweet.fromJSONArray(jsonObject.getJSONArray("statuses")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });

    }
}
