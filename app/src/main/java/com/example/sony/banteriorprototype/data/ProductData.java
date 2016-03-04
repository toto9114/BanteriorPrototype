package com.example.sony.banteriorprototype.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sony on 2016-02-22.
 */
public class ProductData{
    @SerializedName("furniture_url")
    public String productImage;
    public String brand;
    public String name;
    @SerializedName("no")
    public String productId;
    public String size;
    @SerializedName("color_id")
    public String color;
    public String link;
}
