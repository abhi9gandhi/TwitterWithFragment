package com.codepath.apps.twitterclient;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.twitterclient.Fragment.UserProfileFragment;
import com.codepath.apps.twitterclient.Fragment.UserTimelineFragment;
import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;


public class UserActivity extends ActionBarActivity {
    UserTimelineFragment usertimefrag;
    UserProfileFragment userprofilefrag;
    private TwitterClient client;
    User user;
    private String screen_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        screen_name = getIntent().getStringExtra("screen_name");
        client = TwitterApp.getRestClient();
        client.getUserInfo(screen_name,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                 user = new User(response, 0);
                    getSupportActionBar().setTitle(user.getScreenName());

                userprofilefrag = UserProfileFragment.newInstance(user);
                usertimefrag = UserTimelineFragment.newInstance(screen_name);

                    getSupportFragmentManager().beginTransaction().replace(R.id.userprofile, userprofilefrag).commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.usertimeline, usertimefrag).commit();
    //            getSupportFragmentManager().beginTransaction().replace(R.layout.fragment_timeline, usertimefrag).commit();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
