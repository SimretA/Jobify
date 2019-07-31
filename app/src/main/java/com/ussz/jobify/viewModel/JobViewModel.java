package com.ussz.jobify.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ussz.jobify.data.Job;
import com.ussz.jobify.network.JobRemote;

import java.util.List;

public class JobViewModel extends ViewModel {
    private MutableLiveData<List<Job>> persistentJobs;

    private void loadJobs(){
        JobRemote.getAllJobs(jobs -> persistentJobs.setValue((List<Job>) jobs));
    }
    public LiveData<List<Job>> getJobs(){
        if(persistentJobs == null){
            persistentJobs = new MutableLiveData<>();
            loadJobs();
        }
        return persistentJobs;
    }
}
