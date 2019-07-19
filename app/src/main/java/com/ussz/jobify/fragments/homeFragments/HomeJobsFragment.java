package com.ussz.jobify.fragments.homeFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ussz.jobify.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeJobsFragment extends Fragment {


    public HomeJobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_jobs, container, false);
    }

}
