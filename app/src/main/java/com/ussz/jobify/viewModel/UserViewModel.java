package com.ussz.jobify.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserViewModel extends ViewModel {
    private static final DatabaseReference USER_DATA_REF =
            FirebaseDatabase.getInstance().getReference();

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(USER_DATA_REF);

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }

}
