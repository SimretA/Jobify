package com.ussz.jobify.fragments.homeFragments;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ussz.jobify.R;
import com.ussz.jobify.adapters.HomeJobsListAdapter;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.network.JobRemote;
import com.ussz.jobify.viewModel.JobViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeJobsFragment extends Fragment {


    HomeJobsListAdapter homeJobsListAdapter;

    public HomeJobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_jobs, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.homeJobsRecyclerView);


        homeJobsListAdapter = new HomeJobsListAdapter(this,new ArrayList<>());
        recyclerView.setAdapter(homeJobsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        JobViewModel jobViewModel = ViewModelProviders.of(this).get(JobViewModel.class);
        jobViewModel.getJobs().observe(this, jobs -> homeJobsListAdapter.setJobs((ArrayList<Job>) jobs));

        return rootView;
    }
    public void listen(ArrayList<Job> jobs){
        homeJobsListAdapter.setJobs(jobs);
    }

}
