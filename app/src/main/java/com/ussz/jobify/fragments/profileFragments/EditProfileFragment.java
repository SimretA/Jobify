package com.ussz.jobify.fragments.profileFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ussz.jobify.R;
import com.ussz.jobify.network.EditRemote;
import com.ussz.jobify.utilities.IEditResult;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment implements IEditResult {


    private TextInputLayout textInputLayout;
    private TextInputEditText updateProfileEt;
    private FancyButton editButton;
    private ProgressBar progressBar3;

    private TextView errorMessageTv;


    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        textInputLayout = rootView.findViewById(R.id.textInputLayout);
        updateProfileEt = rootView.findViewById(R.id.updateProfileEt);
        editButton = rootView.findViewById(R.id.editButton);

        errorMessageTv = rootView.findViewById(R.id.editErrorMessage);

        progressBar3 = rootView.findViewById(R.id.progressBar3);

        String[]  registrationOneData = getArguments().getStringArray("messageForEditing");

        configureEditFragmentBasedOnData(registrationOneData);



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showViews();
                String data = updateProfileEt.getText().toString();
                editData(data,registrationOneData[0]);
            }
        });


        progressBar3.setVisibility(View.GONE);

        return rootView;
    }

    private void editData(String data, String registrationOneDatum) {
        switch (registrationOneDatum){
            case "username":
                EditRemote.updateUserName(data,EditProfileFragment.this);
                break;
            case "email":
                EditRemote.updateEmail(data,EditProfileFragment.this);
                break;
            case "phonenumber":
                EditRemote.updatePhoneNumber(data,EditProfileFragment.this);
                break;
            case "university":
//                EditRemote.updateUniversity(data,EditProfileFragment.this);
                break;
            case "graduationyear":
                EditRemote.updateGraduationYear(Integer.parseInt(data),EditProfileFragment.this);
                break;
            case "department":
                EditRemote.updateDepartment(data,EditProfileFragment.this);
                break;
        }
    }


    private void showSuccessMessage(String message){
        errorMessageTv.setTextColor(getResources().getColor(R.color.green));
        errorMessageTv.setText(message);

    }

    private void showFailureMessage(String message){
        errorMessageTv.setTextColor(getResources().getColor(R.color.red));
        errorMessageTv.setText(message);
    }

    private void showViews(){
        progressBar3.setVisibility(View.VISIBLE);
        updateProfileEt.setEnabled(false);
        editButton.setEnabled(false);
    }

    private void hideViews(){
        progressBar3.setVisibility(View.GONE);
        updateProfileEt.setEnabled(true);
        editButton.setEnabled(true);
    }

    private void configureEditFragmentBasedOnData(String[] registrationOneData) {
        String databeingEdited = registrationOneData[0];
        String hint = "";
        int inputType = R.string.text;
        if (databeingEdited.equals("username")){
            hint = "username";
        }
        else if(databeingEdited.equals("email")){
            hint = "email";
            inputType = R.string.textEmailAddress;
        }
        else if(databeingEdited.equals("phonenumber")){
             hint =  "phonenumber";
             inputType = R.string.phone;
        }
        else if(databeingEdited.equals("university")){
            hint = "university";
        }
        else if(databeingEdited.equals("graduationyear")){
            hint = "graduationyear";
            inputType = R.string.number;
        }
        else if(databeingEdited.equals("department")){
            hint = "department";
        }

        textInputLayout.setHint(hint);
        updateProfileEt.setInputType(inputType);
        updateProfileEt.setText(registrationOneData[1]);
        editButton.setText("Update " + hint);

    }

    @Override
    public void editResult(String result) {
            hideViews();
            if(result.equals("Transaction success!")){
                showSuccessMessage("update success");
            }
            else{
                showFailureMessage("update failed");
            }
    }
}
