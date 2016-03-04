package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sony on 2016-02-23.
 */
public class InteriorPagerAdapter extends FragmentPagerAdapter {
    List<InteriorContentData> items = new ArrayList<>();
    private static final int FRAGMENT_COUNT = 4;

    public InteriorPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position<FRAGMENT_COUNT) {
            return DetailInteriorFragment.newInstance(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}
