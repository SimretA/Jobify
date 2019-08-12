package com.ussz.jobify.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.data.Meetup;
import com.ussz.jobify.utilities.CustomCallback;
import com.ussz.jobify.utilities.FilterCallBack;
import com.ussz.jobify.utilities.RemoteFilterCallback;

import java.io.FilterInputStream;
import java.util.ArrayList;

public class MeetupRemote {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference meetups = db.collection("/meetups");

    public static  void  getMeetups(CustomCallback callback){
        ArrayList<Meetup> newMeetups = new ArrayList<>();

        meetups.get()
                .addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc: task.getResult()){
                                    newMeetups.add(doc.toObject(Meetup.class));
                                }
                                callback.onCallBack(newMeetups);

                            }
                            else
                                Log.d("dataerror", task.getException().toString());
                        }
                );
    }


    public static void getWithDepartment(String department, RemoteFilterCallback remoteFilterCallback){
        ArrayList<Meetup> meetupArrayList = new ArrayList<>();

        meetups.whereEqualTo("target.department", department)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc: task.getResult()){
                                meetupArrayList.add(doc.toObject(Meetup.class));
                            }

                            remoteFilterCallback.onCallBack(meetupArrayList,"Meetups with "+department+" department");
                        }
                        else
                            Log.d("dataerror", task.getException().toString());

                    }
                });
    }


    public static  void getWithOrganization(String department, String organization,RemoteFilterCallback remoteFilterCallback){
        ArrayList<Meetup> meetupArrayList = new ArrayList<>();

        meetups.whereEqualTo("target.department", department)
                .whereGreaterThanOrEqualTo("organizationName",organization)
                .orderBy("organizationName", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc: task.getResult()){
                                meetupArrayList.add(doc.toObject(Meetup.class));
                            }

                            remoteFilterCallback.onCallBack(meetupArrayList,"Meetups with "+department+" department and organization "+organization);
                        }
                        else
                            Log.d("dataerror", task.getException().toString());

                    }
                });
    }


    public static  void getWithMeetUpName(String department, String meetupName,RemoteFilterCallback remoteFilterCallback){
        ArrayList<Meetup> meetupArrayList = new ArrayList<>();

        meetups.whereEqualTo("target.department", department)
                .whereGreaterThanOrEqualTo("name",meetupName)
                .orderBy("name", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc: task.getResult()){
                                meetupArrayList.add(doc.toObject(Meetup.class));
                            }

                            remoteFilterCallback.onCallBack(meetupArrayList,"Meetups with "+department+" department and name "+meetupName);
                        }
                        else
                            Log.d("dataerror", task.getException().toString());

                    }
                });
    }

}
