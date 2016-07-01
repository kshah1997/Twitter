package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = TwitterApplication.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                if(getIntent().getParcelableExtra("user")!= null) {
                    user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user")); }
                getSupportActionBar().setTitle("@" + user.getScreenName());
                populateUserHeader(user);
            }
        });


        String screenName = getIntent().getStringExtra("screen_name");

        if(savedInstanceState == null) {

            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

            //Display user fragment within this activity
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);


    }

    public void populateUserHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
        TextView tvTagLine = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        tvName.setText(user.getName());
        tvUserName.setText("@ " + user.getScreenName());
        tvTagLine.setText(user.getTagline());
        tvFollowing.setText(user.getFollowing() + " FOLLOWING");
        tvFollowers.setText(user.getFollowers() + " FOLLOWERS");

        Picasso.with(this).load(user.getProfileImageUrl())
                .fit().centerInside()
                .transform(new RoundedCornersTransformation(10, 10))
                .into(ivProfileImage);


    }







}
