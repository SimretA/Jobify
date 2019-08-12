package com.ussz.jobify.fragments.exploreFragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.ussz.jobify.adapters.JobSection;
import com.ussz.jobify.data.Department;
import com.ussz.jobify.data.Meetup;
import com.ussz.jobify.network.DepartmentRemote;
import com.ussz.jobify.network.JobRemote;
import com.ussz.jobify.utilities.CustomCallback;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.utilities.FilterCallBack;
import com.ussz.jobify.viewModel.JobViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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


    private ArrayList<JobSection> jobSectionStack = new ArrayList<>();

    private ConstraintLayout exploreJobsLayout;

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



        exploreJobsLayout = rootView.findViewById(R.id.exploreJobsLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        JobViewModel jobViewModel = ViewModelProviders.of(this).get(JobViewModel.class);
        jobViewModel.setCallback(this);
        ArrayList<String> filter = new ArrayList<>();
        filter.add("software engineering");
        if(!jobViewModel.isStarted()) {
            jobViewModel.getFilteredJobs(Job.FIELD_DEPARTMENT, filter).observe(this, jobSections -> {
                jobViewModel.setStarted();
                refresh(jobSections);
                recyclerView.setAdapter(sectionAdapter);
            });


        }
            jobViewModel.jobSections.observe(this, new Observer<ArrayList<JobSection>>() {
                @Override
                public void onChanged(ArrayList<JobSection> jobSections) {
                    sectionAdapter.removeAllSections();
                    int length = jobSections.size() - 1;
                    for (int i = length; i >= 0; i--) {
                        sectionAdapter.addSection(jobSections.get(i));
                        jobViewModel.setStarted();
                    }
                    recyclerView.setAdapter(sectionAdapter);
                }
            });


        //default
        //JobRemote.getJobWithWithDepartment("software engineering",this);

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


        dialog.findViewById(R.id.filterButton).setOnClickListener(view -> {

            String department = spinner.getSelectedItem().toString();
            String organization = organizationFilterET.getText().toString().trim();
            String salary = salaryFilterEt.getText().toString().trim();
            if (organization.equals("") && salary.equals("")){
                //do with department only
                filter.clear();
                filter.add(department);
                jobViewModel.getFilteredJobs(Job.FIELD_DEPARTMENT,filter);

            }
            else if (organization.equals("")){
                //do with dep and salary
                filter.clear();
                filter.add(salary);
                filter.add(department);
                jobViewModel.getFilteredJobs(Job.FIELD_SALARY+Job.FIELD_DEPARTMENT, filter);
//                    JobRemote.getJobWithSalaryGreaterThan(department,Double.parseDouble(salary),ExploreJobsFragment.this);
            }
            else if (salary.equals("")){
                //do with dep and org
                filter.clear();
                filter.add(department);
                filter.add(organization);
                jobViewModel.getFilteredJobs(Job.FIELD_DEPARTMENT+Job.FIELD_ORGINAZATION, filter);
                //JobRemote.getJobWithOrganization(organization,department,ExploreJobsFragment.this);
            }
            else{
                //all are entered :send separate request for both 2
                filter.clear();
                filter.add(salary);
                filter.add(department);
                filter.add(organization);
                jobViewModel.getFilteredJobs(Job.FIELD_SALARY+ Job.FIELD_DEPARTMENT+ Job.FIELD_ORGINAZATION, filter);
                //JobRemote.getJobWithSalaryGreaterThan(department,Double.parseDouble(salary),ExploreJobsFragment.this);
                //JobRemote.getJobWithOrganization(organization,department,ExploreJobsFragment.this);
            }

            dialog.dismiss();


        });

        dialog.findViewById(R.id.bt_close).setOnClickListener(view -> dialog.dismiss());


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

    @Override
    public void onResult(Object object, String result) {
        List<Job> jobs = (List<Job>) object;
        if (jobs.size()>0){

            jobSectionStack.add(new JobSection(result,jobs));
            sectionAdapter.removeAllSections();
            int length = jobSectionStack.size()-1;
            for (int i=length;i>=0;i--){
                sectionAdapter.addSection(jobSectionStack.get(i));
            }
            recyclerView.setAdapter(sectionAdapter);
        }
        else{
            showNoResultFound();
        }
    }

    public void  refresh(ArrayList<JobSection> jobSections){
        sectionAdapter.removeAllSections();
        int length = jobSections.size() - 1;
        for (int i = length; i >= 0; i--) {
            sectionAdapter.addSection(jobSections.get(i));
        }

    }
    private void showNoResultFound() {
        Snackbar snackbar = Snackbar.make(exploreJobsLayout,getString(R.string.noresultfound),Snackbar.LENGTH_LONG);
        snackbar.setTextColor(getResources().getColor(R.color.green));
        snackbar.show();
    }


    @Override
    public void onCallBack(Object object) {
        if(object == null){
            showNoResultFound();
            return;
        }
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
}
