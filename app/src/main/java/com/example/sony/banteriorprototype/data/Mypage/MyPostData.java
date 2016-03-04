package com.example.sony.banteriorprototype.data.Mypage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sony on 2016-02-22.
 */
public class MyPostData{
    public int post_id;
    @SerializedName("file_url")
    public String interiorImage;
    @SerializedName("photo_url")
    public String profileImage;
    //public List<String> hash_tag;
    public String hash_tag;
}
