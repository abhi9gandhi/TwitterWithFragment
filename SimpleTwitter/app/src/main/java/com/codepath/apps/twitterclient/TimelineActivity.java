package com.codepath.apps.twitterclient;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.twitterclient.Fragment.HomeTimeLineFrgament;
import com.codepath.apps.twitterclient.Fragment.MentionTimelineFragment;
import com.codepath.apps.twitterclient.Fragment.TimelineFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;


public class TimelineActivity extends ActionBarActivity {
    private TwitterClient client;
    private HomeTimeLineFrgament hometimelinefrag;
    private MentionTimelineFragment mentiontimefrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
       // hometimelinefrag = HomeTimeLineFrgament.newInstance();
        mentiontimefrag = MentionTimelineFragment.newInstance();
        client = TwitterApp.getRestClient();
     //   getFragmentManager().beginTransaction().replace(R.id.flcontainer, hometimelinefrag).commit();
        getFragmentManager().beginTransaction().replace(R.id.flcontainer, mentiontimefrag).commit();
    }


    public void sendTweet(String tweet) {
        client.composeTweet(tweet, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               // gettimeline(1,1);
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
