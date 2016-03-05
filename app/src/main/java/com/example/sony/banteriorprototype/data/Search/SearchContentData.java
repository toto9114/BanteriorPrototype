package com.example.sony.banteriorprototype.data.Search;

import com.example.sony.banteriorprototype.data.CommentData;
import com.example.sony.banteriorprototype.data.ProductData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sony on 2016-03-05.
 */
public class SearchContentData {
    public int post_id;
    public String content;
    public String category;
    public String username;
    @SerializedName("file_url")
    public  String interiorImage;
    @SerializedName("photo_url")
    public  String profileImage;
    public  List<String> hash_tag;
    public  int scrap_count;
    public   List<ProductData> furnitures;
    public   List<CommentData> reply;
}
