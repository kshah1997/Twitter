package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    Tweet tweet;
    HomeTimelineFragment homeTimelineFragment;
    MentionsTimelineFragment mentionsTimelineFragment;
    MenuItem miActionProgressItem;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        homeTimelineFragment = new HomeTimelineFragment();
        mentionsTimelineFragment = new MentionsTimelineFragment();


        //Get the viewpager
        //Set the viewpager adapter for the pager
        //find the sliding tabstrip
        //attach the tabstrip to the viewpager

        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);



        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



    }




    public void onComposeClick(MenuItem item) {

        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("new_tweet"));
            homeTimelineFragment.addNew(tweet);

        }
    }

    public void onReplyTweet(View view) {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivity(i);
    }

    //return the order of the fragments in the viewpager
    public class TweetsPagerAdapter extends FragmentPagerAdapter {


    private String tabTitles[] = {"Home", "Mentions"};

    public TweetsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) {
            return homeTimelineFragment;
        }

        else if (position == 1) {
            return mentionsTimelineFragment;
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);

    }

    public void onProfileView(MenuItem mi) {

        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timeline, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();


                Intent i = new Intent(TimelineActivity.this, SearchActivity.class);
                i.putExtra("query", query);
                startActivity(i);

                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }



}
