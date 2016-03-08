package com.example.sony.banteriorprototype.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sony.banteriorprototype.R;
//email: yunji@naver.com
//password: 1234567
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(savedInstanceState ==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.login_container,new LoginFragment())
                    .commit();
        }
    }
    public void changeSignup(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_container,new SignupFragment())
                .addToBackStack(null)
                .commit();
    }
}
