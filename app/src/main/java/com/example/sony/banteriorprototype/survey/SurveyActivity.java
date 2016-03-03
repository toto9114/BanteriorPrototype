package com.example.sony.banteriorprototype.survey;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sony.banteriorprototype.R;

public class SurveyActivity extends AppCompatActivity {

    ViewPager pager;
    SurveyPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        pager = (ViewPager)findViewById(R.id.survey_pager);
        mAdapter = new SurveyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(mAdapter);
    }
    public void moveToNext(){
        int page = pager.getCurrentItem();
            pager.setCurrentItem(page + 1, true);
    }
}
