package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ComposeActivity extends AppCompatActivity {
    User user;
    TwitterClient client;
    EditText etNewTweet;
    String newTweet;
    TweetsArrayAdapter tweetsArrayAdapter;
    Tweet tweet;
    Button btnTweet;
    TextView tvTweetCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        tvTweetCount = (TextView) findViewById(R.id.tvTweetCount);
        btnTweet = (Button) findViewById(R.id.btnTweetCompose);



        client = TwitterApplication.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                populateFields(user);
            }
        });

        etNewTweet = (EditText) findViewById(R.id.etNewTweet);
        etNewTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)

                tvTweetCount.setText(String.valueOf(140 - s.length()));

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
                if(s.length() >= 140) {
                    tvTweetCount.setTextColor(getResources().getColor(R.color.red));
                    btnTweet.setClickable(false);
                }

                else {
                    tvTweetCount.setTextColor(getResources().getColor(R.color.black));
                    btnTweet.setClickable(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed

            }

        });


    }

    private void populateFields(User user) {

        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        tvName.setText(user.getName());
        tvUserName.setText("@ " + user.getScreenName());

        Picasso.with(this).load(user.getProfileImageUrl())
                .fit().centerInside()
                .transform(new RoundedCornersTransformation(10, 10))
                .into(ivProfileImage);

    }

    public void onTweetCompose(View view) {

        etNewTweet = (EditText) findViewById(R.id.etNewTweet);
        newTweet = etNewTweet.getText().toString();
        tweet = new Tweet();
        tweet.setBody(newTweet);
        tweet.setCreatedAt("");
        tweet.setUser(user);

        client.postComposeTweet(newTweet, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("new_tweet", Parcels.wrap(tweet));
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish();
    }

    public void onCloseCompose(View view) {
        finish();
    }



}
