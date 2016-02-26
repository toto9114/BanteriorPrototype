package com.example.sony.banteriorprototype.data;

import android.content.Context;

/**
 * Created by sony on 2016-02-22.
 */
public class NetworkManager {
    private static NetworkManager instance;
    public static NetworkManager getInstance(){
        if(instance ==null){
            instance = new NetworkManager();
        }
        return instance;
    }

    private NetworkManager(){

    }

    public interface OnResultListener<T>{
        public void onSuccess(T result);
        public void onFailure(int code);
    }

    public void login(Context context, String userId, String password, final OnResultListener<String> listener){

    }
    public void signup(Context context,String userId, String name, String password, final OnResultListener<String> listener){

    }
}
