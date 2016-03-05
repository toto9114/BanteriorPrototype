package com.example.sony.banteriorprototype.main.MainInterior;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-23.
 */
public class MainInteriorAdapter extends BaseAdapter {
    List<InteriorContentData> items = new ArrayList<InteriorContentData>();
    @Override
    public int getCount() {
        return items.size();
    }

    public void addMainInterior(InteriorContentData data){
        items.add(data);
        notifyDataSetChanged();
    }
    public void addAll(List<InteriorContentData> data){
        items.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainInteriorView view;
        if(convertView == null){
            view = new MainInteriorView(parent.getContext());
        }
        else {
            view = (MainInteriorView)convertView;
        }
        view.setMainInteriorData(items.get(position));
        return view;
    }
}
