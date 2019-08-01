package com.ussz.jobify.fragments.profileFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.ussz.jobify.R;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.data.University;
import com.ussz.jobify.viewModel.ProfileViewModel;

import java.util.Objects;

import mehdi.sakout.fancybuttons.FancyButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView profileName, profileUniversity, profileGraduationYear, profileDepartment, profileEmail, profilePhoneNumber;

    View rootView;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        FancyButton clickable = rootView.findViewById(R.id.fancyButton2);

        profileName = rootView.findViewById(R.id.tv_name);
        profileUniversity = rootView.findViewById(R.id.profile_university);
        profileDepartment = rootView.findViewById(R.id.profile_department);
        profileGraduationYear = rootView.findViewById(R.id.profile_graduation_year);
        profileEmail = rootView.findViewById(R.id.profile_email);
        profilePhoneNumber = rootView.findViewById(R.id.profile_phone_number);


        FirebaseAuth oAuth = FirebaseAuth.getInstance();

        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        profileViewModel.getMyProfile(Objects.requireNonNull(oAuth.getCurrentUser()).getUid()).observe(this, new Observer<Graduate>() {
            @Override
            public void onChanged(Graduate graduate) {
                setProfileData(graduate);

            }
        });

        rootView.findViewById(R.id.tv_name).setOnClickListener(this);
//        rootView.findViewById(R.id.emailLL).setOnClickListener(this);
        rootView.findViewById(R.id.phoneNumberLL).setOnClickListener(this);
//        rootView.findViewById(R.id.universityLL).setOnClickListener(this);
        rootView.findViewById(R.id.classOfLL).setOnClickListener(this);
        rootView.findViewById(R.id.departmentLL).setOnClickListener(this);



        return rootView;

    }

    private void setProfileData(Graduate graduate){

        profileName.setText(graduate.getName());
        if(graduate.getUniversity() !=null)
            profileUniversity.setText(graduate.getUniversity().getName());

        profileEmail.setText(graduate.getEmail());
        profileDepartment.setText(graduate.getDepartment());
        profileGraduationYear.setText(String.valueOf(graduate.getGraduationYear()));
        profilePhoneNumber.setText(graduate.getPhoneNumber());

    }


    @Override
    public void onClick(View view) {
        String[] messageToEditFragment = new String[2];
        switch (view.getId()){
            case R.id.tv_name:
                messageToEditFragment[0] = "username";
                messageToEditFragment[1] = profileName.getText().toString();
                //username
                break;
            case R.id.emailLL:
                messageToEditFragment[0] = "email";
                messageToEditFragment[1] = profileEmail.getText().toString();
                //email
                break;
            case R.id.phoneNumberLL:
                messageToEditFragment[0] = "phonenumber";
                messageToEditFragment[1] = profilePhoneNumber.getText().toString();
                //phone number
                break;
            case R.id.universityLL:
                messageToEditFragment[0] = "university";
                messageToEditFragment[1] = profileUniversity.getText().toString();
                //university
                break;
            case R.id.classOfLL:
                messageToEditFragment[0] = "graduationyear";
                messageToEditFragment[1] = profileGraduationYear.getText().toString();
                //class of
                break;
            case R.id.departmentLL:
                messageToEditFragment[0] = "department";
                messageToEditFragment[1] = profileDepartment.getText().toString();
                //department
                break;
        }


        navigateToEditFragment(messageToEditFragment,view);
    }

    private void navigateToEditFragment(String[] messageToEditFragment, View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArray("messageForEditing",messageToEditFragment);
        Navigation.findNavController(view).navigate(R.id.toEditProfile,bundle);

    }


}
