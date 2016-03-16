package com.example.sony.banteriorprototype.data.Mypage;


import com.google.gson.annotations.SerializedName;

/**
 * Created by sony on 2016-02-22.
 */
public class MyProfileData {
    String username;
    @SerializedName("photo_url")
    String profileImage;

    public int mypost_count;
    public int myscrap_count;
    public String getProfileImage() {
        return profileImage;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }
}
