package com.ussz.jobify.fragments;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ussz.jobify.R;
import com.ussz.jobify.network.LoginRemote;
import com.ussz.jobify.utilities.ILoginResult;
import com.ussz.jobify.validations.LoginValidations;

import mehdi.sakout.fancybuttons.FancyButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements ILoginResult {


    private TextInputEditText email,password;

    private FancyButton loginButton,createAccountButton;

    private ProgressBar progressBar;

    private TextView loginErrorMessageTv;


    View rootView;

    public LoginFragment() {
        // Required empty public constructor
    }

    NavController navController;


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
        loginErrorMessageTv = rootView.findViewById(R.id.loginErrorMessageTv);


        navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);




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
                    hideViews();
                    LoginRemote.loginUser(userEmail,userPassword,LoginFragment.this);
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

    private void showError(String error){
        loginErrorMessageTv.setText(error);
    }


    @Override
    public void loginResult(String result) {
        if (result.equals("login success")){
            navController.navigate(R.id.toHomeFromLogin);
        }
        else{
            showError(result);
            showViews();
        }
    }


}
