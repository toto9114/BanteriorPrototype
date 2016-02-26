package com.example.sony.banteriorprototype.data;

import com.example.sony.banteriorprototype.R;

import java.util.List;

/**
 * Created by sony on 2016-02-22.
 */
public class InteriorData {
    public static final int MODERN_DATA_INDEX = 0;
    public static final int NORTH_DATA_INDEX = 1;
    public static final int VINTAGE_DATA_INDEX = 2;
    public static final int PROVENCE_DATA_INDEX = 3;

    public int interiorType;
    //public String interiorImage;
    public int interiorImage;
    public int x,y;
    public List<ProductData> productDataList;

    public static final int[] MAIN_INTERIOR_IMAGE = {R.drawable.modern_main1,
            R.drawable.modern_main2,
            R.drawable.modern_main3,
            R.drawable.modern_main4};

}
