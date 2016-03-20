package com.example.sony.banteriorprototype.data.Interior;

import com.example.sony.banteriorprototype.data.CommentData;
import com.example.sony.banteriorprototype.data.ProductData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sony on 2016-02-22.
 */
public class InteriorContentData implements Serializable{
    public int post_id;
    public String username;
    @SerializedName("photo_url")
    public String profileImage;
    @SerializedName("file_url")
    public String interiorImage;
    public int scrap_count;
    public int month_price;
    public String[] hash_tag;
    public String category;
    @SerializedName("package")
    public String packageName;
    public int state;
    @SerializedName("furnitures")
    public List<ProductData> productDataList;
    public List<CommentData> reply;
}
