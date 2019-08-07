package com.ussz.jobify.fragments.exploreFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ussz.jobify.R;
import com.ussz.jobify.adapters.HomeJobsListAdapter;
import com.ussz.jobify.adapters.JobSection;
import com.ussz.jobify.network.JobRemote;
import com.ussz.jobify.utilities.CustomCallback;
import com.ussz.jobify.utilities.CustomOnClickListener;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.viewModel.JobViewModel;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreJobsFragment extends Fragment implements CustomCallback {



    private SectionedRecyclerViewAdapter sectionAdapter;
    private RecyclerView recyclerView;

    public ExploreJobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore_jobs, container, false);


        sectionAdapter = new SectionedRecyclerViewAdapter();

        recyclerView = rootView.findViewById(R.id.exploreJobsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        JobRemote.getJobWithWithDepartment("software engineering",this);


        return rootView;
    }


    @Override
    public void onCallBack(Object object) {
        List<Job> jobs = (List<Job>) object;
        sectionAdapter.addSection(new JobSection("Jobs with your department ",jobs));
        recyclerView.setAdapter(sectionAdapter);
    }
}
