package com.ussz.jobify.fragments.registrationFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ussz.jobify.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragmentOne extends Fragment {


    public RegistrationFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);

        rootView.findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.toRegistrationTwo);
            }
        });


        return rootView;
    }

}
