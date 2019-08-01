package com.ussz.jobify.fragments.exploreFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ussz.jobify.R;
import com.ussz.jobify.adapters.FollowingListAdapter;
import com.ussz.jobify.data.Organization;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreOrganizationFragment extends Fragment {


    public ExploreOrganizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore_organization, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.exploreOrganizationRecyclerView);

//        Organization company = new Organization("Dummy Name","src","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.", 1100);
        ArrayList<Organization> companies = new ArrayList<>();
        for(int i=0; i<10;i++) {
           // companies.add(company);
        }
        FollowingListAdapter followingListAdapter = new FollowingListAdapter(getContext(), companies);
        recyclerView.setAdapter(followingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        return rootView;
    }

}
