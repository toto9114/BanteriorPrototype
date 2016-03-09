package com.example.sony.banteriorprototype.data.Community;


import com.example.sony.banteriorprototype.data.CommentData;
import com.example.sony.banteriorprototype.data.Mypage.ThumbnailData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sony on 2016-02-22.
 */
public class CommunityContentData implements ThumbnailData {
    public int post_id;
    public String username;
    public String content;
    public String catergory;
    @SerializedName("file_url")
    public String mainImage;
    @SerializedName("photo_url")
    public String profileImage;
    public List<String> hash_tag;
    public int scrap_count;
    public List<CommentData> reply;
}
