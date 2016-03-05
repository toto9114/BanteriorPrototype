package com.example.sony.banteriorprototype.data.Interior;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.CommentData;
import com.example.sony.banteriorprototype.data.ProductData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sony on 2016-02-22.
 */
public class InteriorContentData {
    public int post_id;
    public String content;
    String category;
    public String username;
    @SerializedName("photo_url")
    public String profileImage;
    @SerializedName("file_url")
    public String interiorImage;
    public String[] hash_tag;
    int scrap_count;
    @SerializedName("furnitures")
    public List<ProductData> productDataList;
    public List<CommentData> reply;
}
