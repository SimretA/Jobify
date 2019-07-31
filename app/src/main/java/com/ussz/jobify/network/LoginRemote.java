package com.ussz.jobify.network;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.ussz.jobify.R;
import com.ussz.jobify.utilities.ILoginResult;

public class LoginRemote {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static void loginUser(String email, String password,ILoginResult iLoginResult){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            iLoginResult.loginResult("login success");
                        } else {
                            String firebaseErrorMessage = "check your connection";
                            // If sign in fails, display a message to the user.
                            Log.w("User sign up oh! no", "signInWithEmail:failure", task.getException());
                            Exception e = task.getException();
                            if(e instanceof FirebaseAuthInvalidUserException || e instanceof FirebaseAuthInvalidCredentialsException){
                                firebaseErrorMessage = "Invalid credential";
                            }
                            Log.i("login error",task.getException().getLocalizedMessage());
                            iLoginResult.loginResult(firebaseErrorMessage);

                        }
                    }
                });
    }
}
