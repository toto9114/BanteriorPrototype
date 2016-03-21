package com.example.sony.banteriorprototype;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.sony.banteriorprototype.GCM.PropertyManager;
import com.example.sony.banteriorprototype.GCM.RegistrationIntentService;
import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.data.PostTypeResult;
import com.example.sony.banteriorprototype.main.MainActivity;
import com.example.sony.banteriorprototype.survey.SurveyActivity;
import com.facebook.AccessToken;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                doRealStart();
            }
        };
        setUpIfNeeded();
    }

        @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST &&
                resultCode == Activity.RESULT_OK) {
            setUpIfNeeded();
        }
    }

    private void setUpIfNeeded() {
        if (checkPlayServices()) {
            String regId = PropertyManager.getInstance().getRegistrationToken();
            if (!regId.equals("")) {
                doRealStart();
            } else {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }
    boolean isPush;
    private void doRealStart() {
        // activity start...
        new AsyncTask<Void, Void,Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                AccessToken token = AccessToken.getCurrentAccessToken();
                String registrationToken = PropertyManager.getInstance().getRegistrationToken();
                if (token != null) {
                    String accessToken = token.getToken();
                    try {
                        NetworkManager.getInstance().facebookLogin(SplashActivity.this, accessToken, registrationToken, new NetworkManager.OnResultListener<PostTypeResult>() {
                            @Override
                            public void onSuccess(Request request, PostTypeResult result) {
                                Toast.makeText(SplashActivity.this, result.result.message, Toast.LENGTH_SHORT).show();

                                if(result.result.push == 0) {
                                    isPush = false;
                                }
                                else {
                                    isPush = true;
                                }
                                PropertyManager.getInstance().setPush(isPush);

                                if (result.error == null) {
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else{
                    String id = PropertyManager.getInstance().getLocalId();
                    String password = PropertyManager.getInstance().getLocalPassword();
                    if(!id.isEmpty() || !password.isEmpty()) {
                        NetworkManager.getInstance().login(SplashActivity.this, id, password, registrationToken, new NetworkManager.OnResultListener<PostTypeResult>() {
                            @Override
                            public void onSuccess(Request request, PostTypeResult result) {
                                if(result.result.push == 0) {
                                    isPush = false;
                                }
                                else {
                                    isPush = true;
                                }
                                PropertyManager.getInstance().setPush(isPush);
                                if(result.error==null) {
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                    }else {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(SplashActivity.this, SurveyActivity.class));
                                finish();
                            }
                        }, 1500);
                    }
                }
                return null;
            }
        }.execute();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }
}
