package com.mtechsoft.fitmy.v1.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mtechsoft.fitmy.v1.activity.landing.fragment.IntroductionPage1Fragment;
import com.mtechsoft.fitmy.v1.activity.landing.fragment.IntroductionPage2Fragment;
import com.mtechsoft.fitmy.v1.activity.landing.fragment.IntroductionPage3Fragment;
import com.mtechsoft.fitmy.v1.activity.landing.fragment.IntroductionPage4Fragment;
import com.mtechsoft.fitmy.v1.activity.landing.fragment.IntroductionPage5Fragment;

public class IntroductionViewPagerAdapter extends FragmentStatePagerAdapter {
    private final int NUM_PAGES = 5;

    public IntroductionViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new IntroductionPage1Fragment();
            case 1: return new IntroductionPage2Fragment();
            case 2: return new IntroductionPage3Fragment();
            case 3: return new IntroductionPage4Fragment();
            case 4: return new IntroductionPage5Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}