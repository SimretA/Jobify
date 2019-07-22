package com.ussz.jobify.fragments.homeFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.ussz.jobify.adapters.ViewPagerAdapter;
import com.ussz.jobify.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TabLayout tabLayout = rootView.findViewById(R.id.exploreTabs);


        List<Fragment> fragmentList = new ArrayList<Fragment>(2);
        fragmentList.add(new HomeJobsFragment());
        fragmentList.add(new HomeMeetupFragment());



        List<String> stringList = new ArrayList<String>(2);
        stringList.add(getString(R.string.jobs));
        stringList.add(getString(R.string.meetups));

        final ViewPager viewPager = rootView.findViewById(R.id.exploreViewPager);
        final PagerAdapter hvpa = new ViewPagerAdapter(getChildFragmentManager(),fragmentList,stringList);

        viewPager.setAdapter(hvpa);
        tabLayout.setupWithViewPager(viewPager);



        return rootView;
    }




}
