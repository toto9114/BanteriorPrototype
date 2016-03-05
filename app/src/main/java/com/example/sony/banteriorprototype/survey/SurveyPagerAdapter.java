package com.example.sony.banteriorprototype.survey;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sony.banteriorprototype.data.Survey.SurveyData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-24.
 */
public class SurveyPagerAdapter extends FragmentPagerAdapter {
    public static final int SURVEY_FRAGMENT_COUNT = 3;

    List<SurveyData> items = new ArrayList<>();

    public void addAll(List<SurveyData> data){
        items.addAll(data);
    }
    public SurveyPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new AgeSurveyFragment(items.get(position));
            case 1:
                return new FavoriteSurveyFragment(items.get(position));
            case 2:
                return new ImportantSurveyFragment(items.get(position));
        }
        return null;
    }

    @Override
    public int getCount() {
        return SURVEY_FRAGMENT_COUNT;
    }
}
