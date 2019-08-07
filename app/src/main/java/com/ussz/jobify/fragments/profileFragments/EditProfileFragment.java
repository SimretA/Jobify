package com.ussz.jobify.fragments.profileFragments;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ussz.jobify.R;
import com.ussz.jobify.network.EditRemote;
import com.ussz.jobify.utilities.IEditResult;
import com.ussz.jobify.validations.EditValidation;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment implements IEditResult {


    private TextInputLayout textInputLayout;
    private TextInputEditText updateProfileEt;
    private FancyButton editButton;
    private ProgressBar progressBar3;

    private ConstraintLayout updateProfileLayout;




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


        updateProfileLayout = rootView.findViewById(R.id.updateProfileLayout);

        progressBar3 = rootView.findViewById(R.id.progressBar3);

        String[]  registrationOneData = getArguments().getStringArray("messageForEditing");

        configureEditFragmentBasedOnData(registrationOneData);



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = updateProfileEt.getText().toString();
                if(EditValidation.simpleEditDataValidation(registrationOneData[1],data)){
                    showViews();
                    editData(data,registrationOneData[0]);
                }
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
                EditRemote.updateUniversity(data,EditProfileFragment.this);
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
        Snackbar snackbar = Snackbar.make(updateProfileLayout,message,Snackbar.LENGTH_LONG);
        snackbar.setTextColor(getResources().getColor(R.color.green));
        snackbar.show();

    }

    private void showFailureMessage(String message){
        Snackbar snackbar = Snackbar.make(updateProfileLayout,message,Snackbar.LENGTH_LONG);
        snackbar.setTextColor(getResources().getColor(R.color.red));
        snackbar.show();
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



        int inputType = InputType.TYPE_CLASS_TEXT;
        if (databeingEdited.equalsIgnoreCase("username")){
            hint = "username";
        }
        else if(databeingEdited.equalsIgnoreCase("email")){
            hint = "email";
            inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
        }
        else if(databeingEdited.equalsIgnoreCase("phonenumber")){
             hint =  "phone number";
             inputType = InputType.TYPE_CLASS_PHONE;
        }
        else if(databeingEdited.equalsIgnoreCase("university")){
            hint = "university";
        }
        else if(databeingEdited.equalsIgnoreCase("graduationyear")){
            hint = "graduation year";
            inputType = InputType.TYPE_CLASS_NUMBER;
        }
        else if(databeingEdited.equals("department")){
            hint = "department";
        }

        textInputLayout.setHint(hint.toUpperCase());
        updateProfileEt.setInputType(inputType);
        updateProfileEt.setText(registrationOneData[1]);
        editButton.setText(("Update " + hint).toUpperCase());

    }

    @Override
    public void editResult(String result) {
            hideViews();
            if(result.equals("Transaction success!")){
                showSuccessMessage("update success");
                Navigation.findNavController(getView()).navigate(R.id.profile_fragment_dest);
            }
            else{
                showFailureMessage("update failed");
            }
    }
}
