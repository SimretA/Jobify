package com.ussz.jobify.fragments.homeFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ussz.jobify.R;
import com.ussz.jobify.adapters.HomeJobsListAdapter;
import com.ussz.jobify.adapters.OnJobClickedListener;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.utilities.Tags;
import com.ussz.jobify.viewModel.JobViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeJobsFragment extends Fragment implements OnJobClickedListener {


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


        homeJobsListAdapter = new HomeJobsListAdapter(this,new ArrayList<>(), this);
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

    @Override
    public void showJobDetails(Job job, View view) {
        Bundle args = new Bundle();
        args.putSerializable(Tags.JOB_BUNDLE_KEY, job);
        Navigation.findNavController(view).navigate(R.id.fragment_job_detail_dest,args);

    }
}
