package com.example.sony.banteriorprototype.data;

import com.skp.Tmap.TMapPOIItem;

/**
 * Created by sony on 2016-03-03.
 */
public class POIItem {
    public TMapPOIItem poiItem;

    public POIItem(TMapPOIItem poiItem) {
        this.poiItem = poiItem;
    }

    @Override
    public String toString() {
        return poiItem.getPOIName();
    }
}
