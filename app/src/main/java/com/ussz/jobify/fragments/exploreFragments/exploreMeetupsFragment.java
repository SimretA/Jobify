package com.ussz.jobify.fragments.exploreFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ussz.jobify.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class exploreMeetupsFragment extends Fragment {


    public exploreMeetupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore_meetups, container, false);
    }

}
