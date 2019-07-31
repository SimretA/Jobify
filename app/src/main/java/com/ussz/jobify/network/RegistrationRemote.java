package com.ussz.jobify.network;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ussz.jobify.R;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.utilities.IRegistrationResult;

public class RegistrationRemote {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void saveAccount(Graduate graduate, IRegistrationResult iRegistrationResult){
        // Add a new document with a generated ID
        db.collection("graduate")
                .document(graduate.getId())
                .set(graduate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        iRegistrationResult.saveAccountResult("Account saved success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String firebaseErrorResult = "Check your connection";
                        iRegistrationResult.saveAccountResult(firebaseErrorResult);
                        Log.w("Errorinregistration", "Error writing document", e);
                    }
                });
    }

    public static void saveEmailAndPassword(Graduate graduate,String password,IRegistrationResult iRegistrationResult) {
        mAuth.createUserWithEmailAndPassword(graduate.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            graduate.setId(user.getUid());
                            iRegistrationResult.saveEmailAndPasswordResult(graduate,"Registration success");
                            //return graduate..
                        }
                        else{
                            String firebaseErrorResult = "Check your connection";
                            Exception e = task.getException();
                            if(e instanceof FirebaseAuthUserCollisionException){
                                firebaseErrorResult = "The email address is already in use by another account.";
                            }
                            iRegistrationResult.saveEmailAndPasswordResult(graduate,firebaseErrorResult);
                            Log.w("Errorinregistration", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }
}
