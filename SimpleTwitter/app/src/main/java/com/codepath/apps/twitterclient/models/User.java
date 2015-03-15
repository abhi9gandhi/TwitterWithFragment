package com.codepath.apps.twitterclient.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by abgandhi on 3/8/15.
 */
@Table(name = "user")
public class User extends Model {
    @Column(name = "userName")
    private String userName;

    @Column(name = "tweetId")
    private long tweetId;

    @Column(name = "screenName")
    private String screenName;

    @Column(name = "tagLine")
    private String tagLine;

    @Column(name = "followerCount")
    private int followerCount;

    @Column(name = "followingCount")
    private int followingCount;

    @Column(name = "profileUrl")
    private String profileUrl;

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }



    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public User(JSONObject object, long tweetId) {
        try {
            this.userName = object.getString("name");
            this.screenName = object.getString("screen_name");
            this.profileUrl = object.getString("profile_image_url");
            this.tagLine = object.getString("description");
            this.followerCount = object.getInt("followers_count");
            this.followingCount = object.getInt("friends_count");
            this.tweetId = tweetId;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Make sure to have a default constructor for every ActiveAndroid model
    public User(){
        super();
    }


    // Record Finders
    public static Tweet byId(long id) {
        return new Select().from(Tweet.class).where("id = ?", id).executeSingle();
    }

    public static List<Tweet> recentItems() {
        return new Select().from(Tweet.class).orderBy("id DESC").limit("300").execute();
    }

}
