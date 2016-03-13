package com.example.sony.banteriorprototype.login;


import android.content.Intent;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

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
    CallbackManager callbackManager;
    LoginManager loginManager;
    Button loginButton;
    boolean isClickable =false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        loginButton = (Button)view.findViewById(R.id.btn_facebook);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOrLogout();
            }
        });

        idView = (EditText) view.findViewById(R.id.edit_id);
        passwordView = (EditText)view.findViewById(R.id.edit_password);
        mHandler = new Handler();

        final String id = idView.getText().toString();
        final String password = passwordView.getText().toString();



        Button btn = (Button)view.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


    private void loginOrLogout() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null) { //토큰이 있는 경우는 스플래시에서 한다. 이미 가입을 한 상태이므로
            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    //페이스북 로그인 성공
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });
            loginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
            loginManager.setDefaultAudience(DefaultAudience.FRIENDS);
            loginManager.logInWithReadPermissions(this, null);
        } else {
            loginManager.logOut();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(!callbackManager.onActivityResult(requestCode, resultCode, data)){ // 페이스북에서 결과가 내려온게 맞으면 처리하게 하려고. 그냥 super 밑에 처리해도 됨
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
