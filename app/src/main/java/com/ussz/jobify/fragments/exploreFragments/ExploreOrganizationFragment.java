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
import com.ussz.jobify.adapters.FollowingListAdapter;
import com.ussz.jobify.data.Organization;
import com.ussz.jobify.utilities.CustomOnClickListener;
import com.ussz.jobify.viewModel.OrganizationViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreOrganizationFragment extends Fragment implements CustomOnClickListener {


    public ExploreOrganizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore_organization, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.exploreOrganizationRecyclerView);

        OrganizationViewModel organizationViewModel = ViewModelProviders.of(this).get(OrganizationViewModel.class);
        ArrayList<Organization> companies = new ArrayList<>();


        FollowingListAdapter followingListAdapter = new FollowingListAdapter(getContext(), companies, this);
        recyclerView.setAdapter(followingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        organizationViewModel.loadAllOrgs();
        organizationViewModel.organizations.observe(this, organizations -> {
            followingListAdapter.setOrganizations(organizations);

        });
        return rootView;
    }

    @Override
    public void showDetails(Object object, View view) {


    }
}
