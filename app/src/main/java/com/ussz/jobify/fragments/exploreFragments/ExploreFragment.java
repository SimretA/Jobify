package com.ussz.jobify.fragments.exploreFragments;


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
public class ExploreFragment extends Fragment {


    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);


        TabLayout tabLayout = rootView.findViewById(R.id.exploreTabs);

        final ViewPager viewPager = rootView.findViewById(R.id.exploreViewPager);
        final PagerAdapter evpa = new ExploreFragment.ExploreViewPagerAdapter(getChildFragmentManager(),4);

        viewPager.setAdapter(evpa);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }



    public class ExploreViewPagerAdapter extends FragmentPagerAdapter{

        int tabCount;

        public ExploreViewPagerAdapter(FragmentManager fm, int numberOfTabs){
            super(fm);
            this.tabCount = numberOfTabs;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ExploreAllFragment();
                case 1:
                    return new ExploreJobsFragment();
                case 2:
                    return new ExploreMeetupsFragment();
                case 3:
                    return new ExploreOrganizationFragment();
                default:
                    return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return (CharSequence) getString(R.string.all);
                case 1:
                    return getString(R.string.jobs);
                case 2:
                    return getString(R.string.meetups);
                case 3:
                    return (CharSequence) getString(R.string.organization);
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
