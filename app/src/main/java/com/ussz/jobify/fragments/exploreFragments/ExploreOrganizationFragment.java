package com.ussz.jobify.fragments.exploreFragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ussz.jobify.R;
import com.ussz.jobify.adapters.FollowingListAdapter;
import com.ussz.jobify.data.Organization;
import com.ussz.jobify.network.DepartmentRemote;
import com.ussz.jobify.network.OrganizationRemote;
import com.ussz.jobify.utilities.CustomOnClickListener;
import com.ussz.jobify.utilities.Tags;
import com.ussz.jobify.viewModel.OrganizationViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreOrganizationFragment extends Fragment implements CustomOnClickListener {


    private Dialog dialog;
    private Spinner spinner;
    private TextInputLayout salaryL;
    private TextInputEditText organizationET;
    private ProgressBar progressBar5;

    public ExploreOrganizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore_organization, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.exploreOrganizationRecyclerView);


        CustomDialog customDialog = new CustomDialog(getContext());
        dialog = customDialog.build();
        spinner = dialog.findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
        salaryL = dialog.findViewById(R.id.textInputLayout9);
        salaryL.setVisibility(View.GONE);
        progressBar5 = dialog.findViewById(R.id.progressBar5);
        progressBar5.setVisibility(View.GONE);
        organizationET = dialog.findViewById(R.id.organizationFilterET);



        rootView.findViewById(R.id.filterFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        dialog.findViewById(R.id.filterButton).setOnClickListener(view -> {
            String organization = organizationET.getText().toString().trim();
            if (!organization.equals("")){
//                OrganizationRemote.getOrganizationByName(organization,ExploreOrganizationFragment.this);
            }
            dialog.dismiss();
        });

        dialog.findViewById(R.id.bt_close).setOnClickListener(view -> dialog.dismiss());


        OrganizationViewModel organizationViewModel = ViewModelProviders.of(this).get(OrganizationViewModel.class);
        ArrayList<Organization> companies = new ArrayList<>();


        FollowingListAdapter followingListAdapter = new FollowingListAdapter(getContext(), companies, this);
        recyclerView.setAdapter(followingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        organizationViewModel.loadAllOrgs();
        organizationViewModel.organizations.observe(this, followingListAdapter::setOrganizations);
        return rootView;
    }

    @Override
    public void showDetails(Object object, View view) {

        Bundle args = new Bundle();
        args.putSerializable(Tags.ORG_BUNDLE_KEY, (Organization) object);
        Navigation.findNavController(view).navigate(R.id.org_profile_fragment_dest, args);

    }
}
