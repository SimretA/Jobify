package com.ussz.jobify.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ussz.jobify.data.Meetup;
import com.ussz.jobify.network.MeetupRemote;

import java.util.List;

public class MeetupViewModel extends ViewModel {
    private MutableLiveData<List<Meetup>> persistentMeetups;

    private void loadMeetups(){
        MeetupRemote.getMeetups(list -> persistentMeetups.setValue((List<Meetup>) list));

    }

    public LiveData<List<Meetup>> getMeetups(){
        if(persistentMeetups == null){
            persistentMeetups = new MutableLiveData<>();
            loadMeetups();
        }

        return persistentMeetups;
    }

}

