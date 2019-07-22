package com.ussz.jobify.fragments.homeFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ussz.jobify.R;
import com.ussz.jobify.adapters.HomeJobsListAdapter;
import com.ussz.jobify.adapters.HomeMeetupsListAdapter;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.data.Meetup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMeetupFragment extends Fragment {


    public HomeMeetupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_meetup, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.homeMeetupRecyclerView);

        Meetup meetup = new Meetup("Google IO meetup","This is the annual google IO extended meetup","",12);
        ArrayList<Meetup> meetups = new ArrayList<>();
        for(int i=0; i<10;i++) {
            meetups.add(meetup);
        }
        HomeMeetupsListAdapter homeMeetupsListAdapter = new HomeMeetupsListAdapter(this,meetups);
        recyclerView.setAdapter(homeMeetupsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        return rootView;
    }

}
