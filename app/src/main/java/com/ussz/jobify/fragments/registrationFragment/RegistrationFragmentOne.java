package com.ussz.jobify.fragments.registrationFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.ussz.jobify.R;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.validations.RegistrationValidation;

import java.lang.reflect.Array;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragmentOne extends Fragment {

    TextInputEditText username,email,password,phoneNumber;
    TextView registrationStepOneError;

    public RegistrationFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);

        username = rootView.findViewById(R.id.usernameEt);
        email = rootView.findViewById(R.id.emailEt);
        password = rootView.findViewById(R.id.passwordEt);
        phoneNumber = rootView.findViewById(R.id.phoneNumberEt);
        registrationStepOneError = rootView.findViewById(R.id.registrationStepOneError);



        rootView.findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String[] userInput = getDara();

                String validationOutPut = RegistrationValidation.validateStep1(toGraduate(userInput),userInput[2]);

                if(validationOutPut.equals("correct")){
                    showError("");
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("registrationOneData",userInput);
                    Navigation.findNavController(view).navigate(R.id.toRegistrationTwo,bundle);
                }
                else{
                    showError(validationOutPut);
                }

            }
        });

        return rootView;
    }


    private void showError(String error){
        registrationStepOneError.setText(error);
    }


    private Graduate toGraduate(String[] userInput){
        Graduate graduate = new Graduate();
        graduate.setName(userInput[0]);
        graduate.setEmail(userInput[1]);
        graduate.setPhoneNumber(userInput[3]);
        return graduate;
    }


    private String[] getDara(){
        String[] data = new String[4];
        data[0] = RegistrationValidation.Sanitize(username.getText().toString());
        data[1] = RegistrationValidation.Sanitize(email.getText().toString());
        data[2] = RegistrationValidation.Sanitize(password.getText().toString());
        data[3] = RegistrationValidation.Sanitize(phoneNumber.getText().toString());
        return data;
    }

}
