package com.codepath.apps.twitterclient.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.User;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(User user) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", user.getScreenName());
        args.putString("tagline", user.getTagLine());
        args.putString("url", user.getProfileUrl());
        args.putInt("follower_count", user.getFollowerCount());
        args.putInt("following_count", user.getFollowingCount());
        fragment.setArguments(args);

        return fragment;
    }

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        TextView screename = (TextView) view.findViewById(R.id.Tvusername);
        TextView tagline = (TextView) view.findViewById(R.id.Tvtagline);
        TextView follwer = (TextView) view.findViewById(R.id.Tvfollower);
        TextView following = (TextView) view.findViewById(R.id.Tvfollowing);
        ImageView profilePics = (ImageView) view.findViewById(R.id.Ivpic);

        screename.setText(getArguments().getString("screen_name"));
        tagline.setText(getArguments().getString("tagline"));
        follwer.setText(getArguments().getInt("follower_count") + " Follower");
        following.setText(getArguments().getInt("following_count") + " Following");
        Picasso.with(getActivity()).load(getArguments().getString("url")).into(profilePics);

        return view;
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


}
