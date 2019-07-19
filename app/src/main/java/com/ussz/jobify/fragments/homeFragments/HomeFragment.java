package com.ussz.jobify.fragments.homeFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.ussz.jobify.R;

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

        final ViewPager viewPager = rootView.findViewById(R.id.exploreViewPager);
        final PagerAdapter hvpa = new HomeViewPagerAdapter(getChildFragmentManager(),2);

        viewPager.setAdapter(hvpa);
        tabLayout.setupWithViewPager(viewPager);



        return rootView;
    }


    public class HomeViewPagerAdapter extends FragmentPagerAdapter{

        int tabCount;

        public HomeViewPagerAdapter(FragmentManager fm,int numberOfTabs){
            super(fm);
            this.tabCount = numberOfTabs;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return (CharSequence) getString(R.string.jobs);
                case 1:
                    return (CharSequence) getString(R.string.meetups);
                default:
                    return null;
            }
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new HomeJobsFragment();
                case 1:
                    return new HomeMeetupFragment();
                 default:
                     return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }

}
