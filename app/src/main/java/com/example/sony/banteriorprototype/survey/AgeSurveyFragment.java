package com.example.sony.banteriorprototype.survey;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.login.LoginActivity;
import com.example.sony.banteriorprototype.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgeSurveyFragment extends Fragment {

    public AgeSurveyFragment() {
        // Required empty public constructor
    }

    //private static final String AGE_FRAGMENT = "age";

    RadioGroup category;
    int age;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_age_survey, container, false);
        Button btn = (Button)view.findViewById(R.id.btn_exit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        category = (RadioGroup)view.findViewById(R.id.category);
        category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(age !=checkedId) {
                    age = checkedId;
                    ((SurveyActivity) getActivity()).moveToNext();
                }
            }
        });
        return view;
    }
}
