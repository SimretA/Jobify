package com.ussz.jobify.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.network.JobRemote;

import java.util.ArrayList;
import java.util.List;

public class JobViewModel extends ViewModel {
    private MutableLiveData<List<Job>> persistentJobs;

    private MutableLiveData<List<Job>> persistFilteredJobs;

    MutableLiveData<List<Job>> pJobs;

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
}
