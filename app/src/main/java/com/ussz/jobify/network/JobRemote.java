package com.ussz.jobify.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.utilities.CustomCallback;
import com.ussz.jobify.utilities.FilterCallBack;
import com.ussz.jobify.utilities.LoginCustomeCallback;
import com.ussz.jobify.utilities.RemoteFilterCallback;

import java.util.ArrayList;
import java.util.List;

public class JobRemote {

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference jobs = db.collection("/jobs");

    public static void getJobWithWithDepartment(String department, RemoteFilterCallback remoteFilterCallback){

        ArrayList<Job> jobsWithDepartment = new ArrayList<>();

        jobs.whereEqualTo("target.department", department)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc: task.getResult()){
                                jobsWithDepartment.add(doc.toObject(Job.class));
                            }
                            remoteFilterCallback.onCallBack(jobsWithDepartment, "Jobs with "+department+" department");

                            //filterCallBack.onResult(jobsWithDepartment,"Jobs with "+department+" department");
                        }
                        else
                            Log.d("dataerror", task.getException().toString());

                    }
                });


    }

    public static void getJobsFromRef(List<DocumentReference> jobs, CustomCallback customCallback) {
        for (DocumentReference doc :
                jobs) {
            doc.get().addOnCompleteListener(task -> {
                customCallback.onCallBack(task.getResult().toObject(Job.class));
            });
        }

    }





    public static void getJobWithSalaryGreaterThan(String department,double salary,RemoteFilterCallback remoteFilterCallback){
        ArrayList<Job> jobsWithSalary = new ArrayList<>();

        jobs.whereEqualTo("target.department",department)
                .whereGreaterThanOrEqualTo("salary", salary)
                .orderBy("salary", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            jobsWithSalary.add(documentSnapshot.toObject(Job.class));
                        }
                        remoteFilterCallback.onCallBack(jobsWithSalary,"");
                       // filterCallBack.onResult(jobsWithSalary,"Jobs with salary > "+salary+" and department "+department);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Compoundqueries",e.toString());
                    }
                });
    }


    public static void getJobWithOrganization(String org,String department,RemoteFilterCallback remoteFilterCallback){
        ArrayList<Job> jobsWithSalary = new ArrayList<>();

        jobs.whereEqualTo("target.department",department)
                .whereGreaterThanOrEqualTo("organizationName", org)
                .orderBy("organizationName", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            jobsWithSalary.add(documentSnapshot.toObject(Job.class));
                        }
                        remoteFilterCallback.onCallBack(jobsWithSalary,"");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Compoundqueries",e.toString());
            }
        });
    }






    public static void getAllJobs(CustomCallback callback){

        ArrayList<Job> newJobs = new ArrayList<>();

        jobs.get()
                .addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc: task.getResult()){
                                    //Log.d("DATASNAP", doc.toObject(Job.class).toString());
                                    newJobs.add(doc.toObject(Job.class));

                                }
                                callback.onCallBack(newJobs);
                            }
                            else
                                Log.d("dataerror", task.getException().toString());
                        }
                );

    }


}
