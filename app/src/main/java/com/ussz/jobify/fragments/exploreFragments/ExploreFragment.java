package com.ussz.jobify.fragments.exploreFragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.ussz.jobify.adapters.ViewPagerAdapter;
import com.ussz.jobify.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {


    private Dialog dialog;

    private String currentTab = "JOBS";


    private TextInputLayout departmentEt,orgEt,salaryEt,locationEt;

    public ExploreFragment() {
        // Required empty public constructor
    }


    HashMap<String,Integer> filterEditTextVisiblity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);


        TabLayout tabLayout = rootView.findViewById(R.id.exploreTabs);


        List<Fragment> fragmentList = new ArrayList<Fragment>(3);
        fragmentList.add(new ExploreJobsFragment());
        fragmentList.add(new ExploreMeetupsFragment());
        fragmentList.add(new ExploreOrganizationFragment());

        filterEditTextVisiblity = new HashMap<>();



        List<String> stringList = new ArrayList<String>(3);
        stringList.add(getString(R.string.jobs));
        stringList.add(getString(R.string.meetups));
        stringList.add(getString(R.string.org));

        final ViewPager viewPager = rootView.findViewById(R.id.exploreViewPager);
        final PagerAdapter evpa = new ViewPagerAdapter(getChildFragmentManager(),fragmentList,stringList);

        viewPager.setAdapter(evpa);
        tabLayout.setupWithViewPager(viewPager);

        rootView.findViewById(R.id.filterFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeAndShowDialog();
            }
        });



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getText().toString().toUpperCase();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return rootView;
    }



    private void makeAndShowDialog(){
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.filterdialog);
        dialog.setCancelable(true);


        departmentEt = dialog.findViewById(R.id.textInputLayout);
        orgEt = dialog.findViewById(R.id.textInputLayout5);
        salaryEt = dialog.findViewById(R.id.textInputLayout9);
        locationEt = dialog.findViewById(R.id.textInputLayout19);


        handleVisibility();


        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void handleVisibility() {

        if (currentTab.equalsIgnoreCase("meetups") ){
            salaryEt.setVisibility(View.GONE);
        }
        else if(currentTab.equalsIgnoreCase("org")){
            salaryEt.setVisibility(View.GONE);
            departmentEt.setVisibility(View.GONE);
        }
    }


}
