package com.ussz.jobify.network;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ussz.jobify.data.Organization;
import com.ussz.jobify.utilities.CustomCallback;

import java.util.List;

public class OrganizationRemote {
    private  static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void getOrganizatinsFromDocument(List<DocumentReference> documentReferenceList, CustomCallback callback){
        for (DocumentReference document :
                documentReferenceList) {
            document.get()
                    .addOnCompleteListener(
                            task -> {
                                callback.onCallBack(task.getResult().toObject(Organization.class));
                                //Log.d(Tags.BUNDLE_KEY, "getOrganizatinsFromDocument: " + task.getResult().toObject(Organization.class));
                            }
                    );

        }

    }
}
