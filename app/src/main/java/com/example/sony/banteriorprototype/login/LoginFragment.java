package com.example.sony.banteriorprototype.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.PostTypeResult;
import com.example.sony.banteriorprototype.main.MainActivity;

import okhttp3.Request;

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
                if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(password)) {
                    NetworkManager.getInstance().login(getContext(), id, password, new NetworkManager.OnResultListener<PostTypeResult>() {
                        @Override
                        public void onSuccess(Request request, PostTypeResult result) {
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(),"입력오류",Toast.LENGTH_SHORT).show();
                }
            }
        });
        signupView = (TextView)view.findViewById(R.id.text_signup);
        signupView.setText(Html.fromHtml("<u>" + signupView.getText().toString()+"</u>"));
        signupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).changeSignup();
            }
        });
        return view;
    }

}
