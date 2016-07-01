package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kshah97 on 6/27/16.
 */
public class TweetsListFragment extends Fragment {

    private TweetsArrayAdapter tweetsAdapter;
    private ArrayList<Tweet> tweets;
    private ListView lvTweets;
    MenuItem miActionProgressItem;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(tweetsAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        tweetsAdapter = new TweetsArrayAdapter(getActivity(), tweets);
    }

   public void addAll(List<Tweet> tweets) {
       tweetsAdapter.addAll(tweets);
   }

    public void addNew(Tweet tweet) {
        tweets.add(0, tweet);
        tweetsAdapter.notifyDataSetChanged();
    }

    public void clearAll(List<Tweet> tweets) {
        tweetsAdapter.clear();
        tweetsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }
}
