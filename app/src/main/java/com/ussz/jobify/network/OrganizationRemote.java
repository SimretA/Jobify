package com.ussz.jobify.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.data.Organization;
import com.ussz.jobify.utilities.CustomCallback;
import com.ussz.jobify.utilities.FilterCallBack;
import com.ussz.jobify.utilities.Tags;

import java.util.ArrayList;
import java.util.List;

public class OrganizationRemote {
    private  static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference orgs = db.collection("/organizations");


    public static void getOrganizationsFromDocument(List<DocumentReference> documentReferenceList, CustomCallback callback){
        ArrayList<Organization> organizations = new ArrayList<>();
        for (DocumentReference document :
                documentReferenceList) {
            document.get()
                    .addOnCompleteListener(
                            task -> {
                                Organization organization = task.getResult().toObject(Organization.class);
                                organization.setFollowing(true);
                                organization.setId(task.getResult().getId());
                                //organization
                                callback.onCallBack(organization);
                                Log.d(Tags.BUNDLE_KEY, "getOrganizationsFromDocument: " + organization);
                            }
                    );

        }


    }
    public static void unfollow(Organization organization){
        WriteBatch batch = db.batch();

        FirebaseAuth oAuth = FirebaseAuth.getInstance();

        DocumentReference orgRef = db.collection("/organizations").document(organization.getId());

        //update following list
        DocumentReference gradRef = db.collection("/graduate").document(oAuth.getCurrentUser().getUid());
        batch.update(gradRef, "following", FieldValue.arrayRemove(orgRef));
        //batch.update(gradRef,"following",organizations);

        //decrease number of followers
        batch.update(orgRef, "followers", FieldValue.increment(-1));

        //commit batch
        batch.commit().addOnCompleteListener(task -> {
            //TODO take action maybe
        });
    }

    public static void follow( Organization organization){
        Log.d("", "follow: "+organization.getId());
        WriteBatch batch = db.batch();

        FirebaseAuth oAuth = FirebaseAuth.getInstance();

        DocumentReference orgRef = db.collection("/organizations").document(organization.getId());

        //update following list
        DocumentReference gradRef = db.collection("/graduate").document(oAuth.getCurrentUser().getUid());
        batch.update(gradRef, "following", FieldValue.arrayUnion(orgRef));

        //increase number of followers
        batch.update(orgRef, "followers", FieldValue.increment(1));

        //commit batch
        batch.commit().addOnCompleteListener(task -> {
            //TODO take action maybe
        });
    }

    public static void getOrganizations(CustomCallback customCallback){

        orgs.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc: task.getResult()){
                            Organization tempOrg = doc.toObject(Organization.class);

                            //Log.d("DATASNAP", doc.toObject(Job.class).toString());
                            tempOrg.setId(doc.getId());
                            customCallback.onCallBack(tempOrg);

                        }

                    }
                    else
                        Log.d("dataerror", task.getException().toString());
                });

    }

    public static void getOrganzationByName(String orgName, FilterCallBack filterCallBack){
        ArrayList<Organization> organizations = new ArrayList<>();

        orgs.whereGreaterThanOrEqualTo("organizationName", orgName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc: task.getResult()){
                                organizations.add(doc.toObject(Organization.class));
                            }

                            filterCallBack.onResult(organizations,"");
                        }
                        else
                            Log.d("dataerror", task.getException().toString());

                    }
                });
    }


}
