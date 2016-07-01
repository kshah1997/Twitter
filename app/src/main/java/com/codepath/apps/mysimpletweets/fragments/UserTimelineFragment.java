package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kshah97 on 6/27/16.
 */
public class UserTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    MenuItem miActionProgressItem;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
        setHasOptionsMenu(true);
        //getActivity().invalidateOptionsMenu();
    }


    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userFragment.setArguments(args);
        return userFragment;
    }


    //send an API request to get the timeline JSON
    //fill the listview by creating the tweet objects from the json

    private void populateTimeline() {


        String screenName = getArguments().getString("screen_name");

        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                Log.d("DEBUG", array.toString());

                //JSON coming in here
                //Deserialize JSON
                //create models and add them to the adapter
                //load the model data into the listview - we re going to need an adapter

                addAll(Tweet.fromJSONArray(array));


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

}








