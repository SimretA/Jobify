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
import com.ussz.jobify.validations.RegistrationValidation;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragmentTwo extends Fragment {

    private TextInputEditText university,classOf,department;

    TextView registrationTwoError;

    Graduate graduate;

    FirebaseFirestore db;

    private FirebaseAuth mAuth;

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

        db = FirebaseFirestore.getInstance();


        rootView.findViewById(R.id.createAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userDepartment = RegistrationValidation.Sanitize(department.getText().toString());
                String userClassOf = RegistrationValidation.Sanitize(classOf.getText().toString());
                String userUniversity = RegistrationValidation.Sanitize(university.getText().toString());

                String validationOutPut = RegistrationValidation.validateStep2(userDepartment,userClassOf,userUniversity);

                int classOfTwoInt = RegistrationValidation.classOfToInt(userClassOf);

                if(validationOutPut.equals("correct") && classOfTwoInt!=0){
                    showError("");
                    graduate.setGraduationYear(Integer.parseInt(userClassOf));
                    graduate.setDepartment(userDepartment);
                    registerGraduate(graduate,password);
                }
                else if(classOfTwoInt==0){
                    showError("Invalid graduation year");
                }
                else{
                    showError(validationOutPut);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
        return rootView;
    }


    private void showError(String error){
        registrationTwoError.setText(error);
    }

    private void registerGraduate(Graduate graduate, String password) {
        //assume the university exists for now\
        saveEmailAndPassword(graduate.getEmail(),password);
    }



    private void saveAccount(Graduate graduate){
        // Add a new document with a generated ID
        db.collection("graduate")
                .document(graduate.getId())
                .set(graduate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Move to home
                        Navigation.createNavigateOnClickListener(R.id.toHomeFromRegistrationTwo,null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Errorinregistration", "Error writing document", e);
                    }
                });
    }

    private void saveEmailAndPassword(String email,String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                graduate.setId(user.getUid());
                                Toast.makeText(getContext(),"success in registration 1",Toast.LENGTH_LONG).show();
                                saveAccount(graduate);
                            }
                            else{
                                Toast.makeText(getContext(),"Error in registration ",Toast.LENGTH_LONG).show();
                                Log.w("Errorinregistration", "createUserWithEmail:failure", task.getException());
                            }
                    }
                });
    }

}
