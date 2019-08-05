package com.ussz.jobify.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.network.ProfileRemote;

public class ProfileViewModel  extends ViewModel {
    MutableLiveData<Graduate> mProfile;

    private void loadProfile(String userID){
        ProfileRemote.getProfile(
                object -> mProfile.setValue((Graduate) object),
                userID);

    }

    public LiveData<Graduate> getMyProfile(String userID){
        if(mProfile == null){
            mProfile = new MutableLiveData<>();
            loadProfile(userID);
        }

        return mProfile;
    }


}
