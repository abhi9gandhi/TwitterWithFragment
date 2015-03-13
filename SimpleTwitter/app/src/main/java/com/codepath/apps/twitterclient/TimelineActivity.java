package com.codepath.apps.twitterclient;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class TimelineActivity extends ActionBarActivity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> atweets;
    private ListView ltweets;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ltweets = (ListView) findViewById(R.id.Lvtweets);
        client = TwitterApp.getRestClient();
        tweets = new ArrayList<>();
        atweets = new TweetsAdaptor(this, tweets);
        ltweets.setAdapter(atweets);

        ltweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);

            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                atweets.clear();
                customLoadMoreDataFromApi(1);
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        long since_id = 1;
        long max_id = 1;
        if (tweets.size() != 0) {
            int index = tweets.size() - 1;
            max_id = tweets.get(index).getTweetId();
        }

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
                Log.d("DEBUG",response.toString());
                tweets = Tweet.parseJsonArray(response);
                atweets.addAll(tweets);
                atweets.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
             //   Log.d("DEBUG",errorResponse.toString());
                Toast.makeText(getApplication(),"NO internet connection", Toast.LENGTH_SHORT);
                atweets.clear();
                atweets.addAll(Tweet.recentTweets());
            }
        });
    }


    public void sendTweet(String tweet) {
        client.composeTweet(tweet, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                gettimeline(1,1);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("DEBUG", "Error");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_compose) {
            Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
            startActivityForResult(i, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (requestCode == 1 && resultCode == 1) {
            // Extract name value from result extras
            String tweet = data.getExtras().getString("tweet");
            sendTweet(tweet);
        }
    }
}
