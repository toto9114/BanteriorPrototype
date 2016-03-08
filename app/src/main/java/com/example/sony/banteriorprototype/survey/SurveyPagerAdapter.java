package com.example.sony.banteriorprototype.survey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.MyApplication;
import com.example.sony.banteriorprototype.data.Survey.Survey;
import com.example.sony.banteriorprototype.data.Survey.SurveyData;
import com.example.sony.banteriorprototype.data.Survey.SurveyResult;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by sony on 2016-02-24.
 */
public class SurveyPagerAdapter extends FragmentPagerAdapter {
    public static final int SURVEY_FRAGMENT_COUNT = 3;

    List<SurveyData> items = new ArrayList<>();


    public SurveyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    AgeSurveyFragment ageSurveyFragment;
    FavoriteSurveyFragment favoriteSurveyFragment;
    ImportantSurveyFragment importantSurveyFragment;


    @Override
    public Fragment getItem(final int position) {
        Bundle bundle = new Bundle();
        switch (position){
            case 0 :
                ageSurveyFragment= new AgeSurveyFragment();
                return ageSurveyFragment;
            case 1:
                favoriteSurveyFragment = new FavoriteSurveyFragment();
                return favoriteSurveyFragment;
            case 2:
                importantSurveyFragment = new ImportantSurveyFragment();
                return importantSurveyFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return SURVEY_FRAGMENT_COUNT;
    }

    public void setSurveyResult(SurveyResult surveyResult) {
        if (ageSurveyFragment != null) {
            ageSurveyFragment.setSurveyResult(surveyResult);
        }
        if(favoriteSurveyFragment != null){
            favoriteSurveyFragment.setSurveyResult(surveyResult);
        }
        if(importantSurveyFragment != null){
            importantSurveyFragment.setSurveyResult(surveyResult);
        }
    }
}
