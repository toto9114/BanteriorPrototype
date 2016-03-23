package com.example.sony.banteriorprototype.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sony on 2016-02-22.
 */
public class ProductData implements Serializable{
    @SerializedName("furniture_url")
    public String productImage;
    public String brand;
    public String name;
    @SerializedName("no")
    public String productId;
    public String size;
    @SerializedName("color_code")
    public String color;
    public String link;
    public String price;
}
