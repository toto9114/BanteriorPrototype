package com.example.sony.banteriorprototype;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;

/**
 * Created by sony on 2016-02-23.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        FacebookSdk.sdkInitialize(this);
    }
    public static Context getContext(){
        return context;
    }
}
