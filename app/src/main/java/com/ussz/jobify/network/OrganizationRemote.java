package com.ussz.jobify.network;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.ussz.jobify.data.Organization;
import com.ussz.jobify.utilities.CustomCallback;
import com.ussz.jobify.utilities.Tags;

import java.util.ArrayList;
import java.util.List;

public class OrganizationRemote {
    private  static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void getOrganizatinsFromDocument(List<DocumentReference> documentReferenceList, CustomCallback callback){
        ArrayList<Organization> organizations = new ArrayList<>();
        for (DocumentReference document :
                documentReferenceList) {
            document.get()
                    .addOnCompleteListener(
                            task -> {
                                Organization organization = task.getResult().toObject(Organization.class);
                                organization.setId(task.getResult().getId());
                                //organization
                                callback.onCallBack(organization);
                                Log.d(Tags.BUNDLE_KEY, "getOrganizatinsFromDocument: " + organization);
                            }
                    );

        }


    }
    public static void unfollow(ArrayList<DocumentReference> organizations, Organization organization){
        WriteBatch batch = db.batch();

        FirebaseAuth oAuth = FirebaseAuth.getInstance();

//        //update following list
//        DocumentReference gradRef = db.collection("/graduate").document(oAuth.getCurrentUser().getUid());
//        batch.update(gradRef,"following",organizations);

        //decrease number of followers
        DocumentReference orgRef = db.collection("/organizations").document(organization.getId());
        batch.update(orgRef, "followers", (organization.getFollowers()-1));

        //commit batch
        batch.commit().addOnCompleteListener(task -> {
            //TODO take action maybe
        });
    }

}
