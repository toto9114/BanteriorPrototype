package com.example.sony.banteriorprototype.survey;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-24.
 */
public class SurveyPagerAdapter extends FragmentPagerAdapter {
    private static final int SURVEY_FRAGMENT_COUNT = 3;

    public SurveyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new AgeSurveyFragment();
            case 1:
                return new FavoriteSurveyFragment();
            case 2:
                return new ImportantSurveyFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return SURVEY_FRAGMENT_COUNT;
    }
}
