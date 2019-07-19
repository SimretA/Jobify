package com.ussz.jobify.adapters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> fragmentTitles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments,List<String> fragmentTitles) {
        super(fm);
        this.fragments = fragments;
        this.fragmentTitles = fragmentTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
