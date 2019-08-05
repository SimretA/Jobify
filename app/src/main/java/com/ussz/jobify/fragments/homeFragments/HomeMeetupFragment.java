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
import android.widget.ProgressBar;

import com.ussz.jobify.R;
import com.ussz.jobify.adapters.HomeMeetupsListAdapter;
import com.ussz.jobify.data.Meetup;
import com.ussz.jobify.utilities.CustomOnClickListener;
import com.ussz.jobify.utilities.Tags;
import com.ussz.jobify.viewModel.MeetupViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMeetupFragment extends Fragment implements CustomOnClickListener {



    private ProgressBar progressbarhomemeetups;

    private HomeMeetupsListAdapter homeMeetupsListAdapter;

    public HomeMeetupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_meetup, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.homeMeetupRecyclerView);

        progressbarhomemeetups = rootView.findViewById(R.id.progressbarhomemeetups);

       
        ArrayList<Meetup> meetups = new ArrayList<>();

        homeMeetupsListAdapter = new HomeMeetupsListAdapter(this,meetups, this);
        recyclerView.setAdapter(homeMeetupsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        MeetupViewModel meetupViewModel = ViewModelProviders.of(this).get(MeetupViewModel.class);
        meetupViewModel.getMeetups().observe(this, meetups1 -> {
            listen((ArrayList<Meetup>) meetups1);
        });


        progressbarhomemeetups.setVisibility(View.VISIBLE);


        return rootView;
    }


    private void listen(ArrayList<Meetup> meetups){

        progressbarhomemeetups.setVisibility(View.GONE);

        homeMeetupsListAdapter.setMeetupsArrayList(meetups);
    }


    @Override
    public void showDetails(Object object, View view) {

        Bundle args = new Bundle();
        args.putSerializable(Tags.MEETUP_BUNDLE_KEY, (Meetup) object);
        Navigation.findNavController(view).navigate(R.id.meetup_detail_fragment_dest,args);

    }
}
