package com.ussz.jobify.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentReference;
import com.ussz.jobify.R;
import com.ussz.jobify.adapters.FollowingListAdapter;
import com.ussz.jobify.data.Organization;
import com.ussz.jobify.utilities.CustomOnClickListener;
import com.ussz.jobify.utilities.Tags;
import com.ussz.jobify.viewModel.OrganizationViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment implements CustomOnClickListener {


    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.following_recycler_view);

        OrganizationViewModel organizationViewModel = ViewModelProviders.of(this).get(OrganizationViewModel.class);


        ArrayList<Organization> companies = new ArrayList<>();


        FollowingListAdapter followingListAdapter = new FollowingListAdapter(getContext(), companies, this);
        recyclerView.setAdapter(followingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        if(getArguments() != null){
            organizationViewModel.loadOrganizationsFromDocument((List<DocumentReference>) getArguments().getSerializable(Tags.BUNDLE_KEY));

        }
        organizationViewModel.organizations.observe(this, organizations -> followingListAdapter.setOrganizations(organizations));
        return view;
    }

    @Override
    public void showDetails(Object object, View view){
        Bundle args = new Bundle();
        args.putSerializable(Tags.ORG_BUNDLE_KEY, (Organization) object);
        Navigation.findNavController(view).navigate(R.id.org_profile_fragment_dest, args);

    }


}
