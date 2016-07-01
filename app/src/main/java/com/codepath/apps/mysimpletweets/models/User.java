package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by kshah97 on 6/26/16.
 */
@Parcel
public class User {

    //list the attributes

     String name;
     long uid;
     String screenName;
     String profileImageUrl;
     String tagline;
     int following;
     int followers;

    public User() {
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    //deserialize the user json => user object

    public static User fromJSON(JSONObject jsonObject) {
        User u = new User();

        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url").replace("_normal", "_bigger");
            u.tagline = jsonObject.getString("description");
            u.followers = jsonObject.getInt("followers_count");
            u.following = jsonObject.getInt("friends_count");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }
}
