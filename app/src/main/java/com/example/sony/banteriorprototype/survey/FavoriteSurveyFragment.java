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
import com.example.sony.banteriorprototype.data.Survey.SurveyData;
import com.example.sony.banteriorprototype.login.LoginActivity;
import com.example.sony.banteriorprototype.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteSurveyFragment extends Fragment {
    public FavoriteSurveyFragment(SurveyData data) {
        // Required empty public constructor
    }

    RadioGroup category;
    int favorite;
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
