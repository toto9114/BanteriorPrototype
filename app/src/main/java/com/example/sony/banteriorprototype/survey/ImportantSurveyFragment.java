package com.example.sony.banteriorprototype.survey;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.login.LoginActivity;
import com.example.sony.banteriorprototype.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImportantSurveyFragment extends Fragment {
    public ImportantSurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_important_survey, container, false);

        Button btn = (Button)view.findViewById(R.id.btn_exit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        btn = (Button)view.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),LoginActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }

}
