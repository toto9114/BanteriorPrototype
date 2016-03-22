package com.example.sony.banteriorprototype.survey;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Survey.SurveyResult;
import com.viewpagerindicator.CirclePageIndicator;

import okhttp3.Request;
public class SurveyActivity extends AppCompatActivity {

    ViewPager pager;
    SurveyPagerAdapter mAdapter;
    CirclePageIndicator circlePageIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        pager = (ViewPager)findViewById(R.id.survey_pager);
        pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
               // page.setTranslationX(page.getWidth() * -position);

//                if(position <= -1.0F || position >= 1.0F) {
//                    page.setAlpha(0.0F);
//                } else if( position == 0.0F ) {
//                    page.setAlpha(1.0F);
//                } else {
//                    // position is between -1.0F & 0.0F OR 0.0F & 1.0F
//                    page.setAlpha(1.0F - Math.abs(position));
//                }
            }
        });
        circlePageIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
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
        circlePageIndicator.setViewPager(pager);
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
