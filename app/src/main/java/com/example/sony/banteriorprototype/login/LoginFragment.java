package com.example.sony.banteriorprototype.login;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import java.io.UnsupportedEncodingException;

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
    Button localLoginBtn;
    String registrationToken;
    String id;
    String password;
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
                   try {
                       NetworkManager.getInstance().facebookLogin(getContext(), registrationToken, facebookToken, new NetworkManager.OnResultListener<PostTypeResult>() {
                           @Override
                           public void onSuccess(Request request, PostTypeResult result) {
                               Toast.makeText(getContext(),result.result.message, Toast.LENGTH_SHORT).show();
                           }

                           @Override
                           public void onFailure(Request request, int code, Throwable cause) {

                           }
                       });
                   } catch (UnsupportedEncodingException e) {
                       e.printStackTrace();
                   }
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
        idView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                isIdEmpty = TextUtils.isEmpty(text);
                isValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordView = (EditText)view.findViewById(R.id.edit_password);
        passwordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                isPasswordEmpty = TextUtils.isEmpty(text);
                isValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mHandler = new Handler();




        localLoginBtn= (Button)view.findViewById(R.id.btn_login);
        localLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = idView.getText().toString();
                password = passwordView.getText().toString();
                registrationToken = PropertyManager.getInstance().getRegistrationToken();
                    NetworkManager.getInstance().login(getContext(), id, password, registrationToken, new NetworkManager.OnResultListener<PostTypeResult>() {
                        @Override
                        public void onSuccess(Request request, PostTypeResult result) {
                            if(result.error!= null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("로그인실패")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                            }else {
                                startActivity(new Intent(getContext(), MainActivity.class));
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {
                            Toast.makeText(getContext(), "Error:" +code, Toast.LENGTH_SHORT).show();
                        }
                    });
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

    boolean isIdEmpty = true, isPasswordEmpty = true;

    private void isValid() {
        if (!isIdEmpty && !isPasswordEmpty) {
            localLoginBtn.setEnabled(true);
        } else {
             localLoginBtn.setEnabled(false);
        }
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
