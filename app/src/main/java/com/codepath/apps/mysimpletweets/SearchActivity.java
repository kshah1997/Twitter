package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.mysimpletweets.fragments.SearchTimelineFragment;

public class SearchActivity extends AppCompatActivity {

    SearchTimelineFragment searchTimelineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String query = getIntent().getStringExtra("query");
        //searchTimelineFragment.searchTweets(query);


        if(savedInstanceState == null) {

            searchTimelineFragment = new SearchTimelineFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flActivitySearch, searchTimelineFragment, "SearchFragment");
            ft.commit();

            searchTimelineFragment.searchTweets(query);


        }

    }

}
