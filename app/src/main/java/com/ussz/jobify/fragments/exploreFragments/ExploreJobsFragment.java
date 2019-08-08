package com.ussz.jobify.fragments.exploreFragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ussz.jobify.R;
import com.ussz.jobify.adapters.HomeJobsListAdapter;
import com.ussz.jobify.adapters.JobSection;
import com.ussz.jobify.data.Department;
import com.ussz.jobify.network.DepartmentRemote;
import com.ussz.jobify.network.JobRemote;
import com.ussz.jobify.utilities.CustomCallback;
import com.ussz.jobify.utilities.CustomOnClickListener;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.utilities.FilterCallBack;
import com.ussz.jobify.viewModel.JobViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreJobsFragment extends Fragment implements FilterCallBack ,CustomCallback {



    private SectionedRecyclerViewAdapter sectionAdapter;
    private RecyclerView recyclerView;

    private Spinner spinner;
    private TextInputLayout organizationL,salaryL;
    private TextInputEditText organizationFilterET,salaryFilterEt;

    private Dialog dialog;

    private ProgressBar progressBar5;

    public ExploreJobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore_jobs, container, false);


        sectionAdapter = new SectionedRecyclerViewAdapter();

        recyclerView = rootView.findViewById(R.id.exploreJobsRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //default
        JobRemote.getJobWithWithDepartment("software engineering",this);

        CustomDialog customDialog = new CustomDialog(getContext());
        dialog = customDialog.build();
        //found on the filter layout
        spinner = dialog.findViewById(R.id.spinner);
        organizationL = dialog.findViewById(R.id.textInputLayout5);
        salaryL = dialog.findViewById(R.id.textInputLayout9);
        progressBar5 = dialog.findViewById(R.id.progressBar5);
        organizationFilterET = dialog.findViewById(R.id.organizationFilterET);
        salaryFilterEt = dialog.findViewById(R.id.salaryFilterEt);


        rootView.findViewById(R.id.filterFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                DepartmentRemote.getAllDepartments(ExploreJobsFragment.this);
                hideViews();
            }
        });


        dialog.findViewById(R.id.filterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String department = spinner.getSelectedItem().toString();
                String organization = organizationFilterET.getText().toString();
                String salary = salaryFilterEt.getText().toString();
                if (organization.equals("") && salary.equals("")){
                    //do with department only
                    JobRemote.getJobWithWithDepartment(department,ExploreJobsFragment.this);
                }
                else if (organization.equals("")){
                    //do with dep and salary
                    JobRemote.getJobWithSalaryGreaterThan(department,Double.parseDouble(salary),ExploreJobsFragment.this);
                }
                else if (salary.equals("")){
                    //do with dep and org
                    JobRemote.getJobWithOrganization(organization,department,ExploreJobsFragment.this);
                }
                else{
                    //all are entered :send separate request for both 2
                    JobRemote.getJobWithSalaryGreaterThan(department,Double.parseDouble(salary),ExploreJobsFragment.this);
                    JobRemote.getJobWithOrganization(organization,department,ExploreJobsFragment.this);
                }

                dialog.dismiss();


            }
        });

        dialog.findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        return rootView;
    }

    private void hideViews() {
        progressBar5.setVisibility(View.VISIBLE);
        spinner.setEnabled(false);
        organizationL.setEnabled(false);
        salaryL.setEnabled(false);
    }

    private void showViews(){
        progressBar5.setVisibility(View.GONE);
        spinner.setEnabled(true);
        organizationL.setEnabled(true);
        salaryL.setEnabled(true);
    }

    Stack<JobSection> jobSectionStack = new Stack<>();

    @Override
    public void onResult(Object object, String result) {
        List<Job> jobs = (List<Job>) object;
        if (jobs.size()>0){
            sectionAdapter.addSection(new JobSection(result,jobs));
            recyclerView.setAdapter(sectionAdapter);
        }
    }


    @Override
    public void onCallBack(Object object) {
        Department department = (Department) object;
        setUpSpinner(department.getDepartments());
        showViews();
    }

    private void setUpSpinner(List departments) {
        Collections.sort(departments);
        ArrayAdapter dataAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, departments);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
}
