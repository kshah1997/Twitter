package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kshah97 on 6/27/16.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    MenuItem miActionProgressItem;


    private TwitterClient client;
    private SwipeRefreshLayout swipeContainer;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
        setHasOptionsMenu(true);

    }

    /*@Override
    public void onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }*/


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateTimeline();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    //send an API request to get the timeline JSON
    //fill the listview by creating the tweet objects from the json

    private void populateTimeline() {

        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                Log.d("DEBUG", array.toString());
                clearAll(Tweet.fromJSONArray(array));


                //JSON coming in here
                //Deserialize JSON
                //create models and add them to the adapter
                //load the model data into the listview - we re going to need an adapter
                addAll(Tweet.fromJSONArray(array));
                swipeContainer.setRefreshing(false);



            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });


    }



}
