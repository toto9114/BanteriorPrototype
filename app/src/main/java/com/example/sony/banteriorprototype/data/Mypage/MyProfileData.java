package com.example.sony.banteriorprototype.data.Mypage;


import com.google.gson.annotations.SerializedName;

/**
 * Created by sony on 2016-02-22.
 */
public class MyProfileData {

    String name;
    @SerializedName("photo_path")
    String profileImage;

    public String getProfileImage() {
        return profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
