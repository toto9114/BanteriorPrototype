package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sony on 2016-02-23.
 */
public class InteriorPagerAdapter extends FragmentStatePagerAdapter {
    List<InteriorContentData> items = new ArrayList<>();

    public void add(InteriorContentData data){
        items.add(data);
        notifyDataSetChanged();
    }
    public void addAll(List<InteriorContentData> data) {
        items.addAll(data);
        notifyDataSetChanged();
    }

    public InteriorContentData getInterior(int position){
        return items.get(position);
    }
    public InteriorPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    DetailInteriorFragment detailInteriorFragment;
    @Override
    public Fragment getItem(int position) {
        detailInteriorFragment = new DetailInteriorFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailInteriorFragment.EXTRA_INTERIOR, items.get(position));
        bundle.putInt(DetailInteriorFragment.EXTRA_POSTION,position);
        detailInteriorFragment.setArguments(bundle);
        return detailInteriorFragment;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
