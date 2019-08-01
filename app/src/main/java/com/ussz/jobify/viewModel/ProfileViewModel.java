package com.ussz.jobify.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.data.University;
import com.ussz.jobify.network.ProfileRemote;
import com.ussz.jobify.network.UniversityRemote;

public class ProfileViewModel  extends ViewModel {
    MutableLiveData<Graduate> mProfile;

    private void loadProfile(String userID){
        ProfileRemote.getProfile(
                object -> {
                    mProfile.setValue((Graduate) object);
                    if (mProfile.getValue() !=null) {
                        UniversityRemote.getUniversityFromDocument(
                                mProfile.getValue().getUniversityRef(),
                                object2 -> {
                                    Graduate newGraduate = mProfile.getValue();
                                    newGraduate.setUniversity((University) object2);
                                    mProfile.setValue(newGraduate);
                                }

                        );
                    }
                    },
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
