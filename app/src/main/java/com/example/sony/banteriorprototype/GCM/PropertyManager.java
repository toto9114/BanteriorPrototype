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

	private static final String FIELD_PUSH = "push";
	public boolean isPush(){
		return mPrefs.getBoolean(FIELD_PUSH,true);
	}

	public void setPush(boolean push){
		mEditor.putBoolean(FIELD_PUSH,push);
		mEditor.commit();
	}

	private static final String FIELD_ID = "id";
	public void setId(int id){
		mEditor.putInt(FIELD_ID, id);
		mEditor.commit();
	}

	public int getId(){
		return mPrefs.getInt(FIELD_LOCAL_ID,-1);
	}
	private static final String FIELD_LOCAL_ID = "Local_Id";
	public void setLocalId(String id){
		mEditor.putString(FIELD_ID, id);
		mEditor.commit();
	}

	public String getLocalId(){
		return mPrefs.getString(FIELD_LOCAL_ID,"");
	}

	private static final String FIELD_LOCAL_PASSWORD = "Local_Password";
	public void setLocalPassword(String password){
		mEditor.putString(FIELD_LOCAL_PASSWORD,password);
		mEditor.commit();
	}

	public String getLocalPassword(){
		return	mPrefs.getString(FIELD_LOCAL_PASSWORD,"");
	}
}
