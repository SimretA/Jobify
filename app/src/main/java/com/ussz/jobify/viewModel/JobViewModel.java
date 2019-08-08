package com.ussz.jobify.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.ussz.jobify.adapters.JobSection;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.network.JobRemote;

import java.util.ArrayList;
import java.util.List;

public class JobViewModel extends ViewModel {
    private MutableLiveData<List<Job>> persistentJobs;

    private MutableLiveData<List<Job>> persistFilteredJobs;

    private MutableLiveData<List<Job>> pJobs;

    public MutableLiveData<ArrayList<JobSection>> jobSections;

    private Boolean started = false;

    public void setStarted(){
        started = true;
    }
    public Boolean isStarted(){
        return started;
    }

    private void loadJobs(){
        JobRemote.getAllJobs(jobs -> persistentJobs.setValue((List<Job>) jobs));
    }

    private void loadFilteredJobs(Graduate graduate,Job job){

    }
    public LiveData<List<Job>> getJobs(){
        if(persistentJobs == null){
            persistentJobs = new MutableLiveData<>();
            loadJobs();
        }
        return persistentJobs;
    }

    private void loadJobsFromRef(List<DocumentReference> jobs) {

        JobRemote.getJobsFromRef(jobs, object -> {
            ArrayList<Job> temp = (ArrayList<Job>) pJobs.getValue();
            temp.add((Job) object);
            pJobs.setValue(temp);
        });

    }
    public LiveData<List<Job>> getJobsFromDoc(List<DocumentReference> jobs){
        if(pJobs == null){
            pJobs= new MutableLiveData<>();
            pJobs.setValue(new ArrayList<>());
            loadJobsFromRef(jobs);
        }
        return pJobs;
    }
    private void loadJobsWithDepartment(String department){
        JobRemote.getJobWithWithDepartment(department, (object, string) -> {
            ArrayList<JobSection> jobSectionArrayList = jobSections.getValue();
            jobSectionArrayList.add(new JobSection("Jobs with "+department+" department", (List<Job>) object));
            jobSections.setValue(jobSectionArrayList);
        });

    }
    private void loadJobsWithDepartmentAndSalary(Double salary, String department){
        JobRemote.getJobWithSalaryGreaterThan(department,salary,(object,string) -> {
            ArrayList<JobSection> jobSectionArrayList = jobSections.getValue();
            jobSectionArrayList.add(new JobSection("Jobs with salary > "+salary+" and department "+department, (List<Job>) object));
            jobSections.setValue(jobSectionArrayList);
        });
    }
    public LiveData<ArrayList<JobSection>> getFilteredJobs(String filterBy, ArrayList<String> filters){
        if (jobSections == null){
            jobSections = new MutableLiveData<>();
            jobSections.setValue(new ArrayList<>());
        }
        if (filterBy.equals("department")){
            loadJobsWithDepartment(filters.get(0));
        }
        else if(filterBy.equals(Job.FIELD_SALARY+Job.FIELD_DEPARTMENT)){

            loadJobsWithDepartmentAndSalary(Double.valueOf(filters.get(0)),filters.get(1));
        }
        return jobSections;
    }


}
