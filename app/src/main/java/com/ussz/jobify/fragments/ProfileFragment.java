package com.ussz.jobify.fragments;


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
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.ussz.jobify.R;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.data.University;
import com.ussz.jobify.utilities.Tags;
import com.ussz.jobify.viewModel.ProfileViewModel;

import java.io.Serializable;
import java.util.Objects;

import mehdi.sakout.fancybuttons.FancyButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView profileName, profileUniversity, profileGraduationYear, profileDepartment, profileEmail, profilePhoneNumber;
    private Graduate mGraduate;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FancyButton clickable = view.findViewById(R.id.profile_following_btn);


        FirebaseAuth oAuth = FirebaseAuth.getInstance();

        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        profileViewModel.getMyProfile(Objects.requireNonNull(oAuth.getCurrentUser()).getUid()).observe(this, new Observer<Graduate>() {
            @Override
            public void onChanged(Graduate graduate) {
                setProfileData(view, graduate);
                mGraduate = graduate;
                clickable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        if(mGraduate != null){
                            args.putSerializable(Tags.BUNDLE_KEY, (Serializable) mGraduate.getFollowing());
                        }
                        Navigation.findNavController(view).navigate(R.id.following_fragment_dest, args);

                    }
                });

            }
        });





        return view;

    }

    private void setProfileData(View view, Graduate graduate){
        profileName = view.findViewById(R.id.tv_name);
        profileUniversity = view.findViewById(R.id.profile_university);
        profileDepartment = view.findViewById(R.id.profile_department);
        profileGraduationYear = view.findViewById(R.id.profile_graduation_year);
        profileEmail = view.findViewById(R.id.profile_email);
        profilePhoneNumber = view.findViewById(R.id.profile_phone_number);
        FancyButton profileFollowing = view.findViewById(R.id.profile_following_btn);

        profileName.setText(graduate.getName());
        if(graduate.getUniversity() !=null)
            profileUniversity.setText(graduate.getUniversity().getName());

        profileEmail.setText(graduate.getEmail());
        profileDepartment.setText(graduate.getDepartment());
        profileGraduationYear.setText(String.valueOf(graduate.getGraduationYear()));
        profilePhoneNumber.setText(graduate.getPhoneNumber());

        profileFollowing.setText(graduate.getFollowing() != null ? "Following " + graduate.getFollowing().size() : "Following 0 ");

    }

}
