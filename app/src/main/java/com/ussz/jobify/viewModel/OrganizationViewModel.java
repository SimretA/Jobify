package com.ussz.jobify.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.data.Organization;
import com.ussz.jobify.network.OrganizationRemote;
import com.ussz.jobify.network.ProfileRemote;

import java.util.ArrayList;
import java.util.List;

public class OrganizationViewModel extends ViewModel {

    public MutableLiveData<ArrayList<Organization>> organizations;
    private Graduate graduate = new Graduate();

    public OrganizationViewModel() {
        ArrayList<Organization> organizationArrayList = new ArrayList<>();
        organizations = new MutableLiveData<>();
        organizations.setValue(organizationArrayList);
    }

    public void loadOrganizationsFromDocument(List<DocumentReference> documentReferenceList){
        if (documentReferenceList!=null && organizations.getValue().size()==0){
            OrganizationRemote.getOrganizationsFromDocument(
                    documentReferenceList, object -> {
                        ArrayList<Organization> newOne = organizations.getValue();
                        newOne.add((Organization) object);
                        organizations.setValue(newOne);
                    }
            );
        }


    }

    public void  loadAllOrgs(){

        if(organizations.getValue().size()==0){

            ProfileRemote.getProfile(object -> {
                this.graduate = (Graduate) object;
                        OrganizationRemote.getOrganizations(object1 -> {
                            ArrayList<Organization> newOne = organizations.getValue();
                            newOne.add(checkFollowing((Organization) object1));
                            organizations.setValue(newOne);
                        });
                    },
                    FirebaseAuth.getInstance().getCurrentUser().getUid());


        }
    }

    private Organization checkFollowing(Organization organization){
        if(graduate.getFollowing() != null){
            for (DocumentReference doc :
                    graduate.getFollowing()) {
                if (doc.getId().equals(organization.getId())){
                    organization.setFollowing(true);
                    return organization;
                }

            }
        }

        organization.setFollowing(false);
        return organization;
    }

}
