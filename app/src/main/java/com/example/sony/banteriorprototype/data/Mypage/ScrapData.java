package com.example.sony.banteriorprototype.data.Mypage;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sony on 2016-02-27.
 */
public class ScrapData implements ThumbnailData {

    public int post_id;
    //@SerializedName("")
    public int interiorImage;
    @SerializedName("photo_url")
    public String profileImage;
    public String[] hash_tag;
}
