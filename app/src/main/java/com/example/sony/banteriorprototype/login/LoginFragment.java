package com.example.sony.banteriorprototype.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    EditText idView;
    EditText passwordView;
    TextView signupView;
    Handler mHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        idView = (EditText) view.findViewById(R.id.edit_id);
        passwordView = (EditText)view.findViewById(R.id.edit_password);
        mHandler = new Handler();
        Button btn = (Button)view.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idView.getText().toString();
                String password = passwordView.getText().toString();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    }
                }, 1500);
            }
        });
        signupView = (TextView)view.findViewById(R.id.text_signup);
        signupView.setText(Html.fromHtml("<u>" + signupView.getText().toString()+"</u>"));
        signupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).changeSignup();
            }
        });
        return view;
    }

}
