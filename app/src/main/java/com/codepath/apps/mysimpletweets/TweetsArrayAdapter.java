package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by kshah97 on 6/26/16.
 */

//Taking the tweets objects and turning them into views displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {


    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context,android.R.layout.simple_list_item_1 ,tweets);
    }


   /* // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(relativeDate.contains("minutes")) {
            relativeDate.replace("minutes", "m");

        }

        else if(relativeDate.contains("seconds")) {
            relativeDate.replace("seconds", "s");

        }

        else if(relativeDate.contains("hour")) {
            relativeDate.replace("hour", "h");

        }

        else if(relativeDate.contains("hours")) {
            relativeDate.replace("hours", "h");
        }

        else{
            return relativeDate;
        }


        return relativeDate;
    }*/


    public static String getTimeDifference(String rawJsonDate) {
        String time = "";
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat format = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        format.setLenient(true);
        try {
            long diff = (System.currentTimeMillis() - format.parse(rawJsonDate).getTime()) / 1000;
            if (diff < 5)
                time = "Just now";
            else if (diff < 60)
                time = String.format(Locale.ENGLISH, "%ds",diff);
            else if (diff < 60 * 60)
                time = String.format(Locale.ENGLISH, "%dm", diff / 60);
            else if (diff < 60 * 60 * 24)
                time = String.format(Locale.ENGLISH, "%dh", diff / (60 * 60));
            else if (diff < 60 * 60 * 24 * 30)
                time = String.format(Locale.ENGLISH, "%dd", diff / (60 * 60 * 24));
            else {
                Calendar now = Calendar.getInstance();
                Calendar then = Calendar.getInstance();
                then.setTime(format.parse(rawJsonDate));
                if (now.get(Calendar.YEAR) == then.get(Calendar.YEAR)) {
                    time = String.valueOf(then.get(Calendar.DAY_OF_MONTH)) + " "
                            + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
                } else {
                    time = String.valueOf(then.get(Calendar.DAY_OF_MONTH)) + " "
                            + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
                            + " " + String.valueOf(then.get(Calendar.YEAR) - 2000);
                }
            }
        }  catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Tweet tweet = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        final ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);

        // Populate the data into the template view using the data object
        tvName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvTimeStamp.setText(getTimeDifference(tweet.getCreatedAt()));
        tvUserName.setText("@" + tweet.getUser().getScreenName());
        ivProfileImage.setImageResource(0);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl())
                .fit().centerInside()
                .transform(new RoundedCornersTransformation(10, 10))
                .into(ivProfileImage);
        // Return the completed view to render on screen

        //ivProfileImage.setTag(1, tweet.getUser().getScreenName());

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(),ProfileActivity.class);
                i.putExtra("user", Parcels.wrap(tweet.getUser()));
                i.putExtra("screen_name",tweet.getUser().getScreenName());
                getContext().startActivity(i);
            }
        });

        return convertView;
    }


}
