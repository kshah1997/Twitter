package com.codepath.apps.mysimpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by kshah97 on 6/26/16.
 */
@Parcel

public class Tweet {

     String body;
     long uid; //unique id for the tweet
     User user;
     String createdAt;

    public Tweet() {
    }

    public User getUser() {
        return user;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getBody() {

        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }


    //Deserialize the JSON and build tweet objects
    //Tweet.fromJSON => Tweet

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        //Extract values from the JSON, store them

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;

    }


    public static ArrayList<Tweet> fromJSONArray(JSONArray array) {

        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i< array.length(); i++) {
            Tweet tweet = null;
            try {
                tweet = fromJSON(array.getJSONObject(i));
                if (tweet != null) {
                tweets.add(tweet); }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }

        return tweets;



    }



}
