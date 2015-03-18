package com.codepath.apps.twitterclient.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterclient.EndlessScrollListener;
import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.TimelineActivity;
import com.codepath.apps.twitterclient.TweetsAdaptor;
import com.codepath.apps.twitterclient.TwitterApp;
import com.codepath.apps.twitterclient.TwitterClient;
import com.codepath.apps.twitterclient.UserActivity;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimelineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> atweets;
    private ListView ltweets;
    public SwipeRefreshLayout swipeContainer;



    public interface Listener  {
        public void customLoadMoreDataFromApi(long since_id, long max_id);
    };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment TimelineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimelineFragment newInstance() {
        TimelineFragment fragment = new TimelineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public TimelineFragment() {
        // Required empty public constructor
    }

    Listener mListener = null;

    public void setListener(Listener l) {
        mListener = l;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        tweets = new ArrayList<>();
        atweets = new TweetsAdaptor(getActivity(), tweets);

    }

    // Append more data into the adapter


    public void addAll(ArrayList<Tweet> tweets) {
        atweets.addAll(tweets);
        atweets.notifyDataSetChanged();
    }

    public void clearArrayAdoptor() {
        atweets.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_timeline, container, false);
        ltweets = (ListView) view.findViewById(R.id.Lvtweets);
        ltweets.setAdapter(atweets);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        ltweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
//                Triggered only when new data needs to be appended to the list
//                Add whatever code is needed to append new items to your AdapterView
                long since_id = 1;
                long max_id = Long.MAX_VALUE;
                if (tweets.size() != 0) {
                    for (int index = 0; index < tweets.size(); index++) {
                        if (tweets.get(index).getTweetId() > since_id) {
                            since_id = tweets.get(index).getTweetId();
                        }

                        if (tweets.get(index).getTweetId() <= max_id) {
                            max_id = tweets.get(index).getTweetId();
                        }
                    }
                 //   max_id = tweets.get(0).getTweetId();
                 //   since_id = tweets.get(index).getTweetId();
                }
                mListener.customLoadMoreDataFromApi(since_id, max_id);
            }
        });

        ltweets.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tweet item = tweets.get(position);
                Intent i = new Intent(getActivity(), UserActivity.class);
                i.putExtra("screen_name", item.getUser().getScreenName());
                startActivity(i);
            }
        });

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
               atweets.clear();
               mListener.customLoadMoreDataFromApi((long)1,(long)1);

                swipeContainer.setRefreshing(false);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        return view;
    }

    public void loadMoreData(long since_id, long max_id) {
        mListener.customLoadMoreDataFromApi(since_id, max_id);
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
