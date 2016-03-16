package com.example.sony.banteriorprototype;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import com.example.sony.banteriorprototype.survey.SurveyActivity;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, SurveyActivity.class));
                finish();
            }
        }, 2000);
//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                doRealStart();
//            }
//        };
//        setUpIfNeeded();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
//    }
//
//    @Override
//    protected void onPause() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
//        super.onPause();
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST &&
//                resultCode == Activity.RESULT_OK) {
//            setUpIfNeeded();
//        }
//    }
//
//    private void setUpIfNeeded() {
//        if (checkPlayServices()) {
//            String regId = PropertyManager.getInstance().getRegistrationToken();
//            if (!regId.equals("")) {
//                doRealStart();
//            } else {
//                Intent intent = new Intent(this, RegistrationIntentService.class);
//                startService(intent);
//            }
//        }
//    }
//
//    private void doRealStart() {
//        // activity start...
//        new AsyncTask<Void, Void,Boolean>() {
//            @Override
//            protected Boolean doInBackground(Void... params) {
//                ServerUtilities.register(SplashActivity.this, PropertyManager.getInstance().getRegistrationToken());
//                return null;
//            }
//        }.execute();
//    }
//
//    private boolean checkPlayServices() {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
//                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//                        finish();
//                    }
//                });
//                dialog.show();
//            } else {
//                finish();
//            }
//            return false;
//        }
//        return true;
//    }
}
