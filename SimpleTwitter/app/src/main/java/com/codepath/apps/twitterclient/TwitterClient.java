package com.codepath.apps.twitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "Osmp4fH801hW6BSBGAZxgMKum";       // Change this
	public static final String REST_CONSUMER_SECRET = "WHxXeMpgY2uUs2JQnfT5xt63EcAVSkypVbCWRnqLAeSTdfCqXQ"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

    public void getHomeTimeline(long since_id, long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 5);
        if (since_id == Long.MAX_VALUE) {
            since_id =1;
        }
        params.put("since_id", since_id);
        if (max_id != Long.MAX_VALUE){
            params.put("max_id", max_id -1);
        }
        Log.d("DEBUG",apiUrl.toString() + since_id);
        getClient().get(apiUrl, params, handler);
    }

    public void getMentionTimeline(long since_id, long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 5);
        if (since_id == Long.MAX_VALUE) {
            since_id = 1;
        }
       params.put("since_id", since_id);
        if (max_id != Long.MAX_VALUE){
            params.put("max_id", max_id - 1);
        }
        Log.d("DEBUG",apiUrl.toString() + since_id);
        getClient().get(apiUrl, params, handler);
    }

    public void getUserTimeline(long since_id, long max_id,String scree_name, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 5);
        if (since_id == Long.MAX_VALUE) {
            since_id =1;
        }
       params.put("since_id", since_id);
        if (max_id != Long.MAX_VALUE){
            params.put("max_id", max_id -1);
        }
        params.put("screen_name", scree_name);
        getClient().get(apiUrl, params, handler);
    }

    public void getUserInfo(String screen_name,AsyncHttpResponseHandler handler) {
        RequestParams params = null;
        String apiUrl;
        if (screen_name.isEmpty() || screen_name.equals("")) {
            apiUrl = getApiUrl("account/verify_credentials.json");
        } else {
            apiUrl = getApiUrl("users/show.json");
            params = new RequestParams();
            params.put("screen_name", screen_name);
        }
        getClient().get(apiUrl, params, handler);
    }

    public void composeTweet(String tweet ,AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", tweet);
        Log.d("DEBUG",apiUrl.toString());
        getClient().post(apiUrl, params, handler);
    }

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}