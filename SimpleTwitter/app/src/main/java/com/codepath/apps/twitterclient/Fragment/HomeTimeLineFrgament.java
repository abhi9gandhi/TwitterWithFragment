package com.codepath.apps.twitterclient.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.twitterclient.TweetsAdaptor;
import com.codepath.apps.twitterclient.TwitterApp;
import com.codepath.apps.twitterclient.TwitterClient;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by abgandhi on 3/13/15.
 */
public class HomeTimeLineFrgament extends TimelineFragment implements TimelineFragment.Listener {
    private TwitterClient client;

    // TODO: Rename and change types and number of parameters
    public static HomeTimeLineFrgament newInstance() {
        HomeTimeLineFrgament fragment = new HomeTimeLineFrgament();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setListener(fragment);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApp.getRestClient();
      //  gettimeline(1,1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(long since_id, long max_id) {


        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter

        gettimeline(since_id, max_id);
    }

    public void gettimeline(long since_id, long max_id) {
        client.getHomeTimeline(since_id, max_id, new JsonHttpResponseHandler() {
            // success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());
                addAll(Tweet.parseJsonArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //   Log.d("DEBUG",errorResponse.toString());
                Toast.makeText(getActivity(), "NO internet connection", Toast.LENGTH_SHORT);
                clearArrayAdoptor();
                addAll((ArrayList<Tweet>) Tweet.recentTweets());
            }
        });
    }
}
