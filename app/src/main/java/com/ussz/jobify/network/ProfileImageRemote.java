package com.ussz.jobify.network;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ussz.jobify.utilities.IUploadImageResult;

import java.io.File;

public class ProfileImageRemote {

    private static FirebaseStorage storage = FirebaseStorage.getInstance();
    private static StorageReference storageRef = storage.getReference();


    public static void uploadImage(Uri file, String userId, IUploadImageResult iUploadImageResult){

        StorageReference spaceRef = storageRef.child("images/"+userId);


        UploadTask uploadTask = spaceRef.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return spaceRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    iUploadImageResult.uploadImageResult(downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                    iUploadImageResult.uploadImageResult("Upload failed");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iUploadImageResult.uploadImageResult("Upload failed");
            }
        });


    }
}
