package com.ussz.jobify.network;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.utilities.CustomCallback;

public class ProfileRemote {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void getProfile(CustomCallback callback, String userID){
        CollectionReference profile = db.collection("/graduate");
        profile.document(userID)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        callback.onCallBack(doc.toObject(Graduate.class));
                        Log.d("PROFILE", doc.toObject(Graduate.class).toString());

                    }
                });


    }
}
