package com.ussz.jobify.network;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ussz.jobify.data.University;
import com.ussz.jobify.data.remoteDataHelpers.UniverityRef;
import com.ussz.jobify.utilities.CustomCallback;

public class UniversityRemote {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void getUniversityFromDocument(DocumentReference documentReference, CustomCallback callback){
        documentReference.get()
                .addOnCompleteListener(
                        task ->{
                            callback.onCallBack(task.getResult().toObject(University.class));
                            Log.d("UNIVERSITY DATA ", task.getResult().toString());
                        }
                );

    }
}
