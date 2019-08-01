package com.ussz.jobify.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentReference;
import com.ussz.jobify.R;
import com.ussz.jobify.adapters.FollowingListAdapter;
import com.ussz.jobify.data.Organization;
import com.ussz.jobify.utilities.Tags;
import com.ussz.jobify.viewModel.OrganizationViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {


    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.following_recycler_view);

        OrganizationViewModel organizationViewModel = ViewModelProviders.of(this).get(OrganizationViewModel.class);


//        Organization organization = new Organization("Dummy Name","src","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.", 1100);
        ArrayList<Organization> companies = new ArrayList<>();
        for(int i=0; i<10;i++) {
            //companies.add(organization);
        }

        FollowingListAdapter followingListAdapter = new FollowingListAdapter(getContext(), companies);
        recyclerView.setAdapter(followingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        if(getArguments() != null){
            organizationViewModel.loadOrganizationsFromDocument((List<DocumentReference>) getArguments().getSerializable(Tags.BUNDLE_KEY));

        }
        organizationViewModel.organizations.observe(this, new Observer<ArrayList<Organization>>() {
            @Override
            public void onChanged(ArrayList<Organization> organizations) {

                followingListAdapter.setOrginizations(organizations);
            }
        });
        return view;
    }

}
