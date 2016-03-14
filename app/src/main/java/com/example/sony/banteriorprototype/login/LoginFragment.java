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

import com.example.sony.banteriorprototype.GCM.PropertyManager;
import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.PostTypeResult;
import com.example.sony.banteriorprototype.main.MainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

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
    AccessTokenTracker tracker;
    CallbackManager callbackManager;
    LoginManager loginManager;
    LoginButton loginButton;
    boolean isClickable =false;

    String registrationToken;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        loginButton = (LoginButton)view.findViewById(R.id.btn_facebook);
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
           @Override
           public void onSuccess(LoginResult loginResult) {
               AccessToken token = AccessToken.getCurrentAccessToken();
               String facebookToken = token.getToken();
               if(token != null) {
                   Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                   NetworkManager.getInstance().facebookLogin(getContext(), registrationToken, facebookToken, new NetworkManager.OnResultListener<PostTypeResult>() {
                       @Override
                       public void onSuccess(Request request, PostTypeResult result) {
                           Toast.makeText(getContext(),result.result.message, Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void onFailure(Request request, int code, Throwable cause) {

                       }
                   });
               }
           }

           @Override
           public void onCancel() {
               Toast.makeText(getContext(),"cancel",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onError(FacebookException error) {
               Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
           }
       });

        idView = (EditText) view.findViewById(R.id.edit_id);
        passwordView = (EditText)view.findViewById(R.id.edit_password);
        mHandler = new Handler();

        final String id = idView.getText().toString();
        final String password = passwordView.getText().toString();
        registrationToken = PropertyManager.getInstance().getRegistrationToken();


        Button btn = (Button)view.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(password)) {
                    NetworkManager.getInstance().login(getContext(), id, password, registrationToken, new NetworkManager.OnResultListener<PostTypeResult>() {
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

//    private void loginOrLogout() {
//        AccessToken token = AccessToken.getCurrentAccessToken();
//        if (token == null) { //토큰이 있는 경우는 스플래시에서 한다. 이미 가입을 한 상태이므로
//            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//                @Override
//                public void onSuccess(LoginResult loginResult) {
//                    //페이스북 로그인 성공
//                    startActivity(new Intent(getContext(), MainActivity.class));
//                    getActivity().finish();
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//
//                @Override
//                public void onError(FacebookException error) {
//
//                }
//            });
//            loginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
//            loginManager.setDefaultAudience(DefaultAudience.FRIENDS);
//            loginManager.logInWithReadPermissions(this, null);
//        } else {
//            loginManager.logOut();
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!callbackManager.onActivityResult(requestCode, resultCode, data)){ // 페이스북에서 결과가 내려온게 맞으면 처리하게 하려고. 그냥 super 밑에 처리해도 됨
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
