package com.codepath.apps.twitterclient.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the ActiveAndroid wiki for more details:
 * https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model
 * 
 */
@Table(name = "tweet")
public class Tweet extends Model {
	// Define table fields

	@Column(name = "user",onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private User user;
    @Column(name = "tweetId")
    private long tweetId;
    @Column(name = "text")
	private String text;
    @Column(name = "createAt")
    private String createdAt;
    @Column(name = "retweetCount")
    private int retweetCount;

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    // Parse model from JSON
	public Tweet(JSONObject object){
		super();

		try {
			this.text = object.getString("text");
            this.createdAt = object.getString("created_at");
            this.retweetCount = object.getInt("retweet_count");

            this.tweetId = object.getLong("id");
            this.user = new User(object.getJSONObject("user"), this.tweetId);
            this.user.save();
            this.save();

        } catch (JSONException e) {
			e.printStackTrace();
		}
	}

    public static ArrayList<Tweet> parseJsonArray(JSONArray ajson) {
        ArrayList<Tweet> array = new ArrayList<Tweet>();
        for (int i = 0; i < ajson.length(); i++) {
            Tweet tweet = null;
            try {
                tweet = new Tweet((JSONObject)ajson.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.add(tweet);
        }
        return array;
    }

    // Make sure to have a default constructor for every ActiveAndroid model
    public Tweet(){
        super();
    }



	public static List<Tweet> recentTweets() {
		return new Select().from(Tweet.class).orderBy("id DESC").limit("300").execute();
	}
}



