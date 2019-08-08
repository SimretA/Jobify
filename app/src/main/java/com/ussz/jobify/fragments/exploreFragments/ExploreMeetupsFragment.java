package com.ussz.jobify.fragments.exploreFragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ussz.jobify.R;
import com.ussz.jobify.adapters.HomeMeetupsListAdapter;
import com.ussz.jobify.adapters.JobSection;
import com.ussz.jobify.adapters.MeetupSection;
import com.ussz.jobify.data.Department;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.data.Meetup;
import com.ussz.jobify.network.DepartmentRemote;
import com.ussz.jobify.network.JobRemote;
import com.ussz.jobify.network.MeetupRemote;
import com.ussz.jobify.utilities.CustomCallback;
import com.ussz.jobify.utilities.CustomOnClickListener;
import com.ussz.jobify.utilities.FilterCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreMeetupsFragment extends Fragment implements CustomCallback , FilterCallBack {


    private Dialog dialog;

    private SectionedRecyclerViewAdapter sectionAdapter;
    private RecyclerView recyclerView;

    private TextInputEditText meetupname , organizationName;
    private TextInputLayout meetUpNameTIL , organizationNameTIL;
    private Spinner spinner;

    private ProgressBar progressBar5;

    private ArrayList<MeetupSection> meetupSections = new ArrayList<>();

    private ConstraintLayout exploreMeetupsLayout;

    public ExploreMeetupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore_meetups, container, false);


        sectionAdapter = new SectionedRecyclerViewAdapter();

        recyclerView = rootView.findViewById(R.id.exploreMeetupRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        exploreMeetupsLayout = rootView.findViewById(R.id.exploreMeetupsLayout);

        CustomDialog customDialog = new CustomDialog(getContext());
        dialog = customDialog.build();
        meetupname = dialog.findViewById(R.id.salaryFilterEt);
        meetupname.setInputType(InputType.TYPE_CLASS_TEXT);
        organizationName = dialog.findViewById(R.id.organizationFilterET);

        meetUpNameTIL = dialog.findViewById(R.id.textInputLayout9);
        meetUpNameTIL.setHint("Meetup name");
        organizationNameTIL = dialog.findViewById(R.id.textInputLayout5);

        spinner = dialog.findViewById(R.id.spinner);
        progressBar5 = dialog.findViewById(R.id.progressBar5);


        MeetupRemote.getWithDepartment("software engineering",ExploreMeetupsFragment.this);




        rootView.findViewById(R.id.filterFAB).setOnClickListener(view -> {
            dialog.show();
            DepartmentRemote.getAllDepartments(ExploreMeetupsFragment.this);
            hideViews();
        });

        dialog.findViewById(R.id.filterButton).setOnClickListener(view -> {

            String department = spinner.getSelectedItem().toString();
            String orgName = organizationName.getText().toString().trim();
            String userMeetUpName = meetupname.getText().toString().trim();

            if (orgName.equals("") && userMeetUpName.equals("")){
                //search with dep only
                MeetupRemote.getWithDepartment(department,ExploreMeetupsFragment.this);
                Log.i("here","here");
            }
            else if(userMeetUpName.equals("")){
                //search with org and dep
                MeetupRemote.getWithOrganization(department,orgName,ExploreMeetupsFragment.this);
            }
            else  if(orgName.equals("")){
                //search with dep and meet
                MeetupRemote.getWithMeetUpName(department,userMeetUpName,ExploreMeetupsFragment.this);
            }
            else{
                //search with all
                MeetupRemote.getWithOrganization(department,orgName,ExploreMeetupsFragment.this);
                MeetupRemote.getWithMeetUpName(department,userMeetUpName,ExploreMeetupsFragment.this);
            }



            dialog.dismiss();


        });

        dialog.findViewById(R.id.bt_close).setOnClickListener(view -> dialog.dismiss());


        return rootView;
    }

    private void hideViews() {
        progressBar5.setVisibility(View.VISIBLE);
        spinner.setEnabled(false);
        meetUpNameTIL.setEnabled(false);
        organizationNameTIL.setEnabled(false);
    }

    private void showViews(){
        progressBar5.setVisibility(View.GONE);
        spinner.setEnabled(true);
        meetUpNameTIL.setEnabled(true);
        organizationNameTIL.setEnabled(true);
    }

    @Override
    public void onCallBack(Object object) {
        showViews();
        Department department = (Department) object;
        setUpSpinner(department.getDepartments());

    }
    private void setUpSpinner(List<String> departments) {
        Collections.sort(departments);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, departments);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onResult(Object object, String result) {
        List<Meetup> meetups = (List<Meetup>) object;
        if (meetups.size()>0){

            meetupSections.add(new MeetupSection(result,meetups));
            sectionAdapter.removeAllSections();
            int length = meetupSections.size()-1;
            for (int i=length;i>=0;i--){
                sectionAdapter.addSection(meetupSections.get(i));
            }
            recyclerView.setAdapter(sectionAdapter);
        }
        else{
            showNoResultFound();
        }
    }

    private void showNoResultFound() {
        Snackbar snackbar = Snackbar.make(exploreMeetupsLayout,getString(R.string.noresultfound),Snackbar.LENGTH_LONG);
        snackbar.setTextColor(getResources().getColor(R.color.green));
        snackbar.show();
    }
}
