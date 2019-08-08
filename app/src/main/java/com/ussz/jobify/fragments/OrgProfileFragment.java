package com.ussz.jobify.fragments;


import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ussz.jobify.R;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.data.Organization;
import com.ussz.jobify.network.JobRemote;
import com.ussz.jobify.utilities.Tags;
import com.ussz.jobify.viewModel.JobViewModel;

import java.io.Serializable;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrgProfileFragment extends Fragment {


    public OrgProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_org_profile, container, false);
        if(getArguments() != null){
            Organization organization = (Organization) getArguments().getSerializable(Tags.ORG_BUNDLE_KEY);
            setContent(view, organization);
            view.findViewById(R.id.org_jobs).setOnClickListener(v ->{
                Bundle args = new Bundle();
                args.putSerializable(Tags.LIST_JOBS_BUNDLE_KEY, (Serializable) organization.getJobs());
                Navigation.findNavController(v).navigate(R.id.home_jobs_fragment_dest, args);


            });
            view.findViewById(R.id.org_meetups).setOnClickListener(v -> {
                //TODO
            });

        }
        return view;
    }

    private void setContent(View view, Organization organization) {



        ((TextView)view.findViewById(R.id.org_profile_name)).setText(organization.getOrganizationName());
        ((TextView)view.findViewById(R.id.org_profile_description)).setText(organization.getOrganizationBio());
        ((FancyButton)view.findViewById(R.id.org_profile_location)).setText(organization.getAddress());
        ((FancyButton)view.findViewById(R.id.org_profile_followers)).setText(organization.getFollowers() >1? organization.getFollowers()+" Followers" : organization.getFollowers()+" Follower");
    }

}
