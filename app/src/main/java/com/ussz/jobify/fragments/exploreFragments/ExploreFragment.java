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


    private String currentTab = "JOBS";


//    private TextInputLayout departmentEt,orgEt,salaryEt,locationEt;

    public ExploreFragment() {
        // Required empty public constructor
    }




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




        List<String> stringList = new ArrayList<String>(3);
        stringList.add(getString(R.string.jobs));
        stringList.add(getString(R.string.meetups));
        stringList.add(getString(R.string.org));

        final ViewPager viewPager = rootView.findViewById(R.id.exploreViewPager);
        final PagerAdapter evpa = new ViewPagerAdapter(getChildFragmentManager(),fragmentList,stringList);

        viewPager.setAdapter(evpa);
        tabLayout.setupWithViewPager(viewPager);







        return rootView;
    }



}
