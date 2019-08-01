package com.ussz.jobify.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.ussz.jobify.data.Organization;
import com.ussz.jobify.network.OrganizationRemote;
import com.ussz.jobify.utilities.CustomCallback;

import java.util.ArrayList;
import java.util.List;

public class OrganizationViewModel extends ViewModel {

    public MutableLiveData<ArrayList<Organization>> organizations;

    public OrganizationViewModel() {
        ArrayList<Organization> organizationArrayList = new ArrayList<>();
        organizations = new MutableLiveData<>();
        organizations.setValue(organizationArrayList);
    }

    public void loadOrganizationsFromDocument(List<DocumentReference> documentReferenceList){
        OrganizationRemote.getOrganizatinsFromDocument(
                documentReferenceList, new CustomCallback() {
                    @Override
                    public void onCallBack(Object object) {
                        ArrayList<Organization> newOne = organizations.getValue();
                        newOne.add((Organization) object);
                        organizations.setValue(newOne);
                    }
                }
               );

    }

}