package com.example.sony.banteriorprototype.survey;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Survey.SurveyData;
import com.example.sony.banteriorprototype.data.Survey.SurveyItem;
import com.example.sony.banteriorprototype.data.Survey.SurveyResult;
import com.example.sony.banteriorprototype.login.LoginActivity;
import com.example.sony.banteriorprototype.main.MainActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteSurveyFragment extends Fragment {
    public FavoriteSurveyFragment() {
       // Required empty public constructor
    }

    public void setSurveyResult(SurveyResult result) {
        if (titleView != null) {
            titleView.setText(result.preferData.get(1).questionary);
            List<SurveyItem> textList = result.preferData.get(1).item;
            item1.setText(textList.get(0).item);
            item2.setText(textList.get(1).item);
            item3.setText(textList.get(2).item);
            item4.setText(textList.get(3).item);
        }
    }

    public static final String EXTRA_FAVORITE_FRAGMENT = "favorite";
    SurveyData data;
    RadioGroup category;
    TextView titleView;
    int favorite;
    RadioButton item1,item2,item3,item4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_survey, container, false);
        Button btn = (Button)view.findViewById(R.id.btn_exit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });

        titleView = (TextView)view.findViewById(R.id.text_title);
        item1 = (RadioButton)view.findViewById(R.id.radio_1);
        item2 = (RadioButton)view.findViewById(R.id.radio_2);
        item3 = (RadioButton)view.findViewById(R.id.radio_3);
        item4 = (RadioButton)view.findViewById(R.id.radio_4);

        SurveyResult result = ((SurveyActivity)getActivity()).getSurveyResult();
        if(result != null) {
            titleView.setText(result.preferData.get(1).questionary);
            List<SurveyItem> textList = result.preferData.get(1).item;
            item1.setText(textList.get(0).item);
            item2.setText(textList.get(1).item);
            item3.setText(textList.get(2).item);
            item4.setText(textList.get(3).item);
        }
        category = (RadioGroup)view.findViewById(R.id.category);
        category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(favorite !=checkedId) {
                    favorite = checkedId;
                    ((SurveyActivity) getActivity()).moveToNext();
                }
            }
        });
        return view;
    }

}
