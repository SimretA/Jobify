package com.ussz.jobify.fragments.registrationFragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ussz.jobify.R;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.network.RegistrationRemote;
import com.ussz.jobify.utilities.IRegistrationResult;
import com.ussz.jobify.validations.RegistrationValidation;

import java.util.concurrent.Executor;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragmentTwo extends Fragment implements IRegistrationResult {

    private TextInputEditText university,classOf,department;

    private TextView registrationTwoError;

    private FancyButton createAccountFButton;

    private ProgressBar progressBar2;

    private Graduate graduate;

    private Spinner genderSpinner;

    private NavController navController;

    public RegistrationFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_registration_fragment_two, container, false);
        String[]  registrationOneData = getArguments().getStringArray("registrationOneData");

        graduate = new Graduate();

        graduate.setName(registrationOneData[0]);
        graduate.setEmail(registrationOneData[1]);
        String password = registrationOneData[2];
        graduate.setPhoneNumber(registrationOneData[3]);


        university = rootView.findViewById(R.id.universityEt);
        classOf = rootView.findViewById(R.id.classOfEt);
        department = rootView.findViewById(R.id.departmentEt);
        registrationTwoError = rootView.findViewById(R.id.registrationTwoError);
        progressBar2 = rootView.findViewById(R.id.progressBar2);
        createAccountFButton = rootView.findViewById(R.id.createAccount);

        genderSpinner = rootView.findViewById(R.id.gender);

        setUpSpinner();


        createAccountFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userDepartment = RegistrationValidation.Sanitize(department.getText().toString());
                String userClassOf = RegistrationValidation.Sanitize(classOf.getText().toString());
                String userUniversity = RegistrationValidation.Sanitize(university.getText().toString());
                String validationOutPut = RegistrationValidation.validateStep2(userDepartment,userClassOf,userUniversity);
                String userGender = genderSpinner.getSelectedItem().toString();

                int classOfTwoInt = RegistrationValidation.classOfToInt(userClassOf);

                if(validationOutPut.equals("correct") && classOfTwoInt!=0){
                    showError("");
                    graduate.setGraduationYear(Integer.parseInt(userClassOf));
                    graduate.setDepartment(userDepartment);
                    graduate.setUniversity(userUniversity);
                    graduate.setGender(userGender.substring(0,1).toUpperCase());//here converted to M,F
                    hideViews();
                    RegistrationRemote.saveEmailAndPassword(graduate,password,RegistrationFragmentTwo.this);

                }
                else if(classOfTwoInt==0){
                    showError("Invalid graduation year");
                }
                else{
                    showError(validationOutPut);
                }
            }
        });

        navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);

        progressBar2.setVisibility(View.GONE);

        return rootView;
    }

    private void setUpSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.possiblegender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
    }


    private void hideViews(){
        progressBar2.setVisibility(View.VISIBLE);
        university.setEnabled(false);
        classOf.setEnabled(false);
        department.setEnabled(false);
        createAccountFButton.setEnabled(false);
    }

    private void showViews(){
        progressBar2.setVisibility(View.GONE);
        university.setEnabled(true);
        classOf.setEnabled(true);
        department.setEnabled(true);
        createAccountFButton.setEnabled(true);
    }


    private void showError(String error){
        registrationTwoError.setText(error);
    }


    @Override
    public void saveAccountResult(String result) {
        if (result.equals("Account saved success")){
            navController.navigate(R.id.toHomeFromRegistrationTwo);
        }
        else{
            showError(result);
            showViews();
        }
    }

    @Override
    public void saveEmailAndPasswordResult(Graduate graduate,String result) {
        if (graduate.getId()!=null && result.equals("Registration success")){
            RegistrationRemote.saveAccount(graduate,RegistrationFragmentTwo.this);
        }
        else{
            showError(result);
            showViews();
        }
    }
}
