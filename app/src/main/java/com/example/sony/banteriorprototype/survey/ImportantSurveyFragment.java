package com.example.sony.banteriorprototype.survey;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Survey.SurveyData;
import com.example.sony.banteriorprototype.data.Survey.SurveyItem;
import com.example.sony.banteriorprototype.data.Survey.SurveyResult;
import com.example.sony.banteriorprototype.login.LoginActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImportantSurveyFragment extends Fragment {
    public ImportantSurveyFragment() {
        // Required empty public constructor
    }

    public void setSurveyResult(SurveyResult result) {
        if (titleView != null) {
            titleView.setText(result.preferData.get(2).questionary);
            List<SurveyItem> textList = result.preferData.get(2).item;
            item1.setText(textList.get(0).item);
            item2.setText(textList.get(1).item);
            item3.setText(textList.get(2).item);
            item4.setText(textList.get(3).item);
        }
    }
    public static final String EXTRA_IMPORTANT_FRAGMENT = "important";
    SurveyData data;
    RadioGroup category;
    TextView titleView;
    RadioButton item1,item2,item3,item4;
    int important;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_important_survey, container, false);

        titleView = (TextView)view.findViewById(R.id.text_title);

        item1 = (RadioButton)view.findViewById(R.id.radio_1);
        item2 = (RadioButton)view.findViewById(R.id.radio_2);
        item3 = (RadioButton)view.findViewById(R.id.radio_3);
        item4 = (RadioButton)view.findViewById(R.id.radio_4);
        SurveyResult result = ((SurveyActivity)getActivity()).getSurveyResult();
        if(result != null) {
            titleView.setText(result.preferData.get(2).questionary);
            List<SurveyItem> textList = result.preferData.get(2).item;
            item1.setText(textList.get(0).item);
            item2.setText(textList.get(1).item);
            item3.setText(textList.get(2).item);
            item4.setText(textList.get(3).item);
        }

        category = (RadioGroup)view.findViewById(R.id.category);
        category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                startActivity(new Intent(getContext(),LoginActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }

}
