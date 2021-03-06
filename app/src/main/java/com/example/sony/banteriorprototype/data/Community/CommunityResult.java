package com.example.sony.banteriorprototype.data.Community;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sony on 2016-03-04.
 */
public class CommunityResult {
    public String message;
    public int page;
    public int listperPage;
    @SerializedName("postData")
    public CommunityData communityData;

    @SerializedName("detailData")
    public CommunityContentData communityDetails;
}
