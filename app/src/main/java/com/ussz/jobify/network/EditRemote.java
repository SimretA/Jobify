package com.ussz.jobify.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.ussz.jobify.utilities.IEditResult;

public class EditRemote {

    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    static FirebaseAuth auth = FirebaseAuth.getInstance();

    final static DocumentReference sfDocRef = db.collection("graduate").document(auth.getCurrentUser().getUid());

    public static void updateUserName(String username, IEditResult iEditResult){
        coreUpdateFunctionality("name",username,iEditResult);
    }

    public static void updatePhoneNumber(String phoneNumber, IEditResult iEditResult){
        coreUpdateFunctionality("phoneNumber",phoneNumber,iEditResult);
    }

    public static void updateEmail(String email, IEditResult iEditResult){
        coreUpdateFunctionality("email",email,iEditResult);
    }

    public static void updateUniversity(String university, IEditResult iEditResult){
        coreUpdateFunctionality("university",university,iEditResult);
    }
    public static void updateGraduationYear(int graduationYear, IEditResult iEditResult){
        coreUpdateFunctionality("graduationYear",graduationYear,iEditResult);
    }
    public static void updateDepartment(String department, IEditResult iEditResult){
        coreUpdateFunctionality("department",department,iEditResult);
    }

    public static void updateProfileImage(String firebaseDownloadUri,IEditResult iEditResult){
        coreUpdateFunctionality("profileImage",firebaseDownloadUri,iEditResult);
    }


    private static void coreUpdateFunctionality(String filedToUpdate,Object newData,IEditResult iEditResult){
        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(sfDocRef);


                transaction.update(sfDocRef,filedToUpdate, newData);

                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Transaction..", "Transaction success!");
                iEditResult.editResult("Transaction success!");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Transaction...", "Transaction failure.", e);
                iEditResult.editResult("Transaction failure.");
            }
        });
    }


}
