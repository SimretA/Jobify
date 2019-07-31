package com.ussz.jobify.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.fragments.homeFragments.HomeJobsFragment;
import com.ussz.jobify.utilities.JobCustomCallback;

import java.util.ArrayList;

public class JobRemote {

    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getJobWithCatagory(String department){
        CollectionReference jobs = db.collection("/jobs");
        jobs.whereEqualTo(Job.FIELD_DEPARTMENT, department)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc: task.getResult()){
                                Log.d("data", doc.getData().toString());
                            }
                        }
                        else
                            Log.d("dataerror", task.getException().toString());

                    }
                });


    }
    public static void getAllJobs(Fragment fragment){
        ArrayList<Job> newJobs = new ArrayList<>();
        CollectionReference jobs = db.collection("/jobs");
        jobs.get()
                .addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc: task.getResult()){
                                    Log.d("DATASNAP", doc.toObject(Job.class).toString());
                                    newJobs.add(doc.toObject(Job.class));
                                }
                                if(fragment instanceof HomeJobsFragment){
                                    ((HomeJobsFragment) fragment).listen(newJobs);
                                }

                            }
                            else
                                Log.d("dataerror", task.getException().toString());
                        }
                );

    }


    public static void getAllJobs(JobCustomCallback callback){

        ArrayList<Job> newJobs = new ArrayList<>();
        CollectionReference jobs = db.collection("/jobs");
        jobs.get()
                .addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc: task.getResult()){
                                    Log.d("DATASNAP", doc.toObject(Job.class).toString());
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
