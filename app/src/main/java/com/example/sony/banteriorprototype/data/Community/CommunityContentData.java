package com.example.sony.banteriorprototype.data.Community;


import com.example.sony.banteriorprototype.data.CommentData;
import com.example.sony.banteriorprototype.data.Mypage.ThumbnailData;
import com.example.sony.banteriorprototype.data.ProductData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sony on 2016-02-22.
 */
public class CommunityContentData implements ThumbnailData {
    public int post_id;
    public String content;
    public String catergory;
    public String username;
    @SerializedName("file_url")
    public String mainImage;
    @SerializedName("photo_url")
    public String profileImage;
    public List<String> hashTag;
    public int scrap_count;
    public List<CommentData> reply;
}