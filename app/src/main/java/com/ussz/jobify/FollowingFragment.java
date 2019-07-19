package com.ussz.jobify;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ussz.jobify.adapters.FollowingListAdapter;
import com.ussz.jobify.data.Company;

import java.util.ArrayList;


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

        Company company = new Company("Dummy Name","src","", 1100);
        ArrayList<Company> companies = new ArrayList<>();
        for(int i=0; i<10;i++) {
            companies.add(company);
        }
        FollowingListAdapter followingListAdapter = new FollowingListAdapter(getContext(), companies);
        recyclerView.setAdapter(followingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        return view;
    }

}
