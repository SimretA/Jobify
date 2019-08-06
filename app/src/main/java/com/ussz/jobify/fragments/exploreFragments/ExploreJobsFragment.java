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
import com.ussz.jobify.utilities.CustomOnClickListener;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.viewModel.JobViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreJobsFragment extends Fragment implements CustomOnClickListener {


    public ExploreJobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore_jobs, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.exploreJobsRecyclerView);

        //Job job = new Job("Software engineering","We are looking for software engineering graduates..",23, "Software Engineering");
        ArrayList<Job> jobs = new ArrayList<>();
        for(int i=0; i<10;i++) {
          //  jobs.add(job);
        }
        HomeJobsListAdapter homeJobsListAdapter = new HomeJobsListAdapter(this,jobs, this);
        recyclerView.setAdapter(homeJobsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);





        return rootView;
    }


    @Override
    public void showDetails(Object object, View view) {

    }
}
