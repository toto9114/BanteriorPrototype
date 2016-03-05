package com.example.sony.banteriorprototype.survey;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Survey.Survey;
import com.example.sony.banteriorprototype.data.Survey.SurveyData;

import okhttp3.Request;

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
        NetworkManager.getInstance().getSurvey(this, new NetworkManager.OnResultListener<Survey>() {
            @Override
            public void onSuccess(Request request, Survey result) {
                mAdapter.addAll(result.result.preferData);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }
    public void moveToNext(){
        int page = pager.getCurrentItem();
            pager.setCurrentItem(page + 1, true);
    }
}
