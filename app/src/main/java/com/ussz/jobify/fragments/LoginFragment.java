package com.ussz.jobify.fragments;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ussz.jobify.R;
import com.ussz.jobify.validations.LoginValidations;

import mehdi.sakout.fancybuttons.FancyButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private TextInputEditText email,password;

    private FancyButton loginButton,createAccountButton;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    View rootView;

    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        };


        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login, container, false);


        email = rootView.findViewById(R.id.loginEmailEt);
        password = rootView.findViewById(R.id.loginPasswordEt);

        loginButton = rootView.findViewById(R.id.loginButton);

        createAccountButton = rootView.findViewById(R.id.loginCreateAccountButton);


        mAuth = FirebaseAuth.getInstance();


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.toRegistrationOne);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                if(LoginValidations.validateEmailAndPassword(userEmail,userPassword).equals("correct")){
                    //                signInUser(email.getText().toString(), ,view);
                }
                else{
                    //There is invalid input...
                }
            }
        });

        progressBar = rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        return rootView;
    }


    private void hideViews(){
        progressBar.setVisibility(View.VISIBLE);
        email.setEnabled(false);
        password.setEnabled(false);
        loginButton.setEnabled(false);
        createAccountButton.setEnabled(false);

    }

    private void showViews(){
        progressBar.setVisibility(View.GONE);
        email.setEnabled(true);
        password.setEnabled(true);
        loginButton.setEnabled(true);
        createAccountButton.setEnabled(true);

    }



    private void signInUser(String email, String password,View view){

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Navigation.findNavController(view).navigate(R.id.toHomeFromLogin);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("User sign up oh! no", "signInWithEmail:failure", task.getException());

                        }
                    }
                });

        progressBar.setVisibility(View.GONE);

    }




}
