package com.example.sony.banteriorprototype.data.Mypage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sony on 2016-02-22.
 */
public class MyPostData implements ThumbnailData {
    public int post_id;
    public String interiorImage;

    @SerializedName("photo_url")
    String profileImage;
    List<String> hashtag;
}
