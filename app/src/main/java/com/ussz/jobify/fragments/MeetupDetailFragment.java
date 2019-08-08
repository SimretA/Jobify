package com.ussz.jobify.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ussz.jobify.R;
import com.ussz.jobify.data.Meetup;
import com.ussz.jobify.utilities.Tags;
import com.ussz.jobify.utilities.TimeStampConverter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeetupDetailFragment extends Fragment {


    public MeetupDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meetup_detail, container, false);
        if(getArguments() != null){
            Meetup meetup = (Meetup) getArguments().getSerializable(Tags.MEETUP_BUNDLE_KEY);
            setContent(view, meetup);
        }
        return view;
    }

    private void setContent(View view, Meetup meetup) {
        ((TextView)view.findViewById(R.id.meetup_detail_title)).setText(meetup.getName());
        ((TextView)view.findViewById(R.id.meetup_detail_description)).setText(meetup.getDescription());
        ((TextView)view.findViewById(R.id.meetup_where)).setText(meetup.getLocation());
        ((TextView)view.findViewById(R.id.meetup_when)).setText(TimeStampConverter.timeLeft(meetup.getWhen().toDate()));
        ((TextView)view.findViewById(R.id.meetup_by)).setText(meetup.getOrganizationName());


    }

}
