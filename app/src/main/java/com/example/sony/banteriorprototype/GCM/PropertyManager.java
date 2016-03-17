package com.example.sony.banteriorprototype.GCM;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.sony.banteriorprototype.MyApplication;

public class PropertyManager {
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	
	private PropertyManager() {
		mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
		mEditor = mPrefs.edit();
	}

	private static final String REG_ID = "regToken";
	private static final String FACEBOOK_ID = "facebookToken";
	public void setFacebookId(String userId){
		mEditor.putString(FACEBOOK_ID,userId);
		mEditor.commit();
	}
	public String getFacebookId(){
		return mPrefs.getString(FACEBOOK_ID,"");
	}



	public void setRegistrationToken(String regId) {
		mEditor.putString(REG_ID, regId);
		mEditor.commit();
	}
	
	public String getRegistrationToken() {
		return mPrefs.getString(REG_ID, "");
	}
	
}
