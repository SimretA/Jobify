package com.ussz.jobify.network;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.data.Meetup;
import com.ussz.jobify.utilities.CustomCallback;

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


    public static void filterMeetups(Graduate graduate,Meetup meetup){

    }
}
