package com.ussz.jobify.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.ussz.jobify.R;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.data.University;

import mehdi.sakout.fancybuttons.FancyButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView profileName, profileUniversity, profileGraduationYear, profileDepartment, profileEmail, profilePhoneNumber;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FancyButton clickable = view.findViewById(R.id.fancyButton2);


        Graduate jane = new Graduate("yuolu","Female","Jane Doe", "Software Engineering", 2019, "", new University("1","Addis Ababa University", "registrar@aait.edu"),"09234567","janedoe@aait.edu");

        profileName = view.findViewById(R.id.tv_name);
        profileUniversity = view.findViewById(R.id.profile_university);
        profileDepartment = view.findViewById(R.id.profile_department);
        profileGraduationYear = view.findViewById(R.id.profile_graduation_year);
        profileEmail = view.findViewById(R.id.profile_email);
        profilePhoneNumber = view.findViewById(R.id.profile_phone_number);

        profileName.setText(jane.getName());
        profileUniversity.setText(jane.getUniversity().getName());
        profileEmail.setText(jane.getEmail());
        profileDepartment.setText(jane.getDepartment());
        profileGraduationYear.setText(String.valueOf(jane.getGraduationYear()));
        profilePhoneNumber.setText(jane.getPhoneNumber());


        return view;

    }

}
