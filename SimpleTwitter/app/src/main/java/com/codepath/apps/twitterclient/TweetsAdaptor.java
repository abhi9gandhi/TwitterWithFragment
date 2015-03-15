package com.codepath.apps.twitterclient;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * Created by abgandhi on 3/8/15.
 */
public class TweetsAdaptor extends ArrayAdapter<Tweet> {
    public TweetsAdaptor(Context context, ArrayList<Tweet> list) {
        super(context, android.R.layout.simple_list_item_1, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet, parent, false);
        }

        TextView username = (TextView) convertView.findViewById(R.id.Tvusername);
        TextView createAt = (TextView) convertView.findViewById(R.id.Tvtime);
        TextView text = (TextView) convertView.findViewById(R.id.Tvtext);
        ImageView profilePics = (ImageView) convertView.findViewById(R.id.IvprofilePic);



        username.setText(tweet.getUser().getName());
        String relativeDate = getRelativeTimeAgo(tweet.getCreatedAt());
        createAt.setText(relativeDate);
        text.setText(tweet.getText());
        profilePics.setImageResource(0);
        Picasso.with(getContext()).load(tweet.getUser().getProfileUrl()).into(profilePics);
        return convertView;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
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

        return relativeDate;
    }
    // public  TweetsAdaptor(Context context, ArrayList<Tweet> list) {
  //      super(context,android.R.layout.simple_list_item_1 ,list);
  //  }

}
