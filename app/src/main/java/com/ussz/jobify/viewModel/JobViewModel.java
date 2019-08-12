package com.ussz.jobify.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.ussz.jobify.adapters.JobSection;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.network.JobRemote;
import com.ussz.jobify.utilities.CustomCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JobViewModel extends ViewModel {
    private MutableLiveData<List<Job>> persistentJobs;

    private MutableLiveData<List<Job>> persistFilteredJobs;

    private MutableLiveData<List<Job>> pJobs;

    public MutableLiveData<ArrayList<JobSection>> jobSections;

    private Boolean started = false;

    private CustomCallback customCallback;
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
            List<Job> temp  = ((List)object);
            if (temp.size() == 0){
                if (customCallback !=null)
                    this.customCallback.onCallBack(null);
            }
            else {
                ArrayList<JobSection> jobSectionArrayList = jobSections.getValue();
                jobSectionArrayList.add(new JobSection("Jobs with "+department+" department", temp));
                jobSections.setValue(jobSectionArrayList);
            }

        });

    }

    private void loadJobsWithDepartmentAndSalary(Double salary, String department){
        JobRemote.getJobWithSalaryGreaterThan(department,salary,(object,string) -> {
            List<Job> temp  = ((List)object);
            if (temp.size() == 0){
                if (customCallback !=null)
                    this.customCallback.onCallBack(null);
                return;
            }
            ArrayList<JobSection> jobSectionArrayList = jobSections.getValue();
            jobSectionArrayList.add(new JobSection("Jobs with salary more than "+salary+" and department "+department, temp));
            jobSections.setValue(jobSectionArrayList);
        });
    }
    private void loadJobsWithDepartmentAndOrganization(String department, String organization){
        JobRemote.getJobWithOrganization(organization,department, ((object, string) -> {
            List<Job> temp  = ((List)object);
            if (temp.size() == 0){
                if (customCallback !=null)
                    this.customCallback.onCallBack(null);
                return;
            }
            ArrayList<JobSection> jobSectionArrayList = jobSections.getValue();
            jobSectionArrayList.add(new JobSection("Jobs with organization "+organization+" and department "+ department, temp));
        }));
    }
    private void loadJobWithAllFilters(Double salary, String department, String organization){
        JobRemote.getJobWithSalaryGreaterThan(department, salary,((object, string) -> {
            ArrayList<JobSection> jobSectionArrayList = jobSections.getValue();
            ArrayList<Job> temp = (ArrayList<Job>) object;
            JobRemote.getJobWithOrganization(organization, department,((object1, string1) -> {
                if(temp.size()==0 && ((List)object1).size()==0){
                    if (customCallback !=null)
                        this.customCallback.onCallBack(null);
                }
                else{
                    temp.addAll((Collection<? extends Job>) object1);
                    jobSectionArrayList.add(new JobSection("Jobs with organization "+organization+" and department "+ department+" and salary more than "+ salary,
                            temp));
                }

            }));
        }));
    }
    public LiveData<ArrayList<JobSection>> getFilteredJobs(String filterBy, ArrayList<String> filters){
        if (jobSections == null){
            jobSections = new MutableLiveData<>();
            jobSections.setValue(new ArrayList<>());
        }
        switch (filterBy) {
            case Job.FIELD_DEPARTMENT:
                loadJobsWithDepartment(filters.get(0));
                break;
            case Job.FIELD_SALARY + Job.FIELD_DEPARTMENT:

                loadJobsWithDepartmentAndSalary(Double.valueOf(filters.get(0)), filters.get(1));
                break;
            case Job.FIELD_DEPARTMENT+Job.FIELD_ORGINAZATION:
                loadJobsWithDepartmentAndOrganization(filters.get(0), filters.get(1));
                break;
            case Job.FIELD_SALARY+Job.FIELD_DEPARTMENT+Job.FIELD_ORGINAZATION:
                loadJobWithAllFilters(Double.valueOf(filters.get(0)),filters.get(1), filters.get(2));
                break;
                default:
                    break;
        }
        return jobSections;
    }

    public void setCallback(CustomCallback customCallback){
        this.customCallback =customCallback;
    }


}
