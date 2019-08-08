package com.ussz.jobify.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ussz.jobify.data.Department;
import com.ussz.jobify.utilities.CustomCallback;

public class DepartmentRemote {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static DocumentReference departmentRef = db.collection("/department").document("NlQC8RS0brR8eD1hBHNd");

    public static void getAllDepartments(CustomCallback customCallback){
        departmentRef.
                get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Department department = document.toObject(Department.class);
                        customCallback.onCallBack(department);
                        Log.d("departments", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("departments", "No such document");
                    }
                } else {
                    Log.d("departments", "get failed with ", task.getException());
                }
            }
        });
    }

}
