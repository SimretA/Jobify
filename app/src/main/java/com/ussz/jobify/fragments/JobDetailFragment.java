package com.ussz.jobify.fragments;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ussz.jobify.R;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.utilities.Tags;
import com.ussz.jobify.utilities.TimeStampConverter;


/**
 * A simple {@link Fragment} subclass.
 */
public class JobDetailFragment extends Fragment {


    public JobDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            Job job = (Job) bundle.getSerializable(Tags.JOB_BUNDLE_KEY);
            setContent(view, job);
        }

        return  view;
    }

    private void setContent(View view, Job job) {
        ((TextView)view.findViewById(R.id.job_detail_title)).setText(job.getTitle());
        ((TextView)view.findViewById(R.id.job_detail_description)).setText(job.getDescription());
        ((TextView)view.findViewById(R.id.job_detail_how_to_apply)).setText(job.getHowToApply());
        ((TextView)view.findViewById(R.id.job_detail_deadline)).setText(TimeStampConverter.timeLeft(job.getDeadline().toDate()) + " \nDays Left" );
        ((TextView)view.findViewById(R.id.job_detail_studentLimit)).setText(String.valueOf(job.getStudentLimit()));
        ((AppCompatImageView)view.findViewById(R.id.job_detail_image)).setImageResource(R.drawable.avataaars);

    }



}
