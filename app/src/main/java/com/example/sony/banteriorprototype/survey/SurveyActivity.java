package com.example.sony.banteriorprototype.survey;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Survey.Survey;
import com.example.sony.banteriorprototype.data.Survey.SurveyData;
import com.example.sony.banteriorprototype.data.Survey.SurveyResult;

import java.util.ArrayList;
import java.util.List;

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
        NetworkManager.getInstance().setSurvey(this, new NetworkManager.OnResultListener<SurveyResult>() {
            @Override
            public void onSuccess(Request request, SurveyResult result) {
                surveyResult = result;
                mAdapter.setSurveyResult(result);
//                List<SurveyData> items = new ArrayList<SurveyData>();
//                for(SurveyData data : result.preferData){
//                    items.add(data);
//                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
        pager.setAdapter(mAdapter);
    }

    SurveyResult surveyResult = null;
    public SurveyResult getSurveyResult() {
        return surveyResult;
    }

    public void moveToNext(){
        int page = pager.getCurrentItem();
            pager.setCurrentItem(page + 1, true);
    }
}
