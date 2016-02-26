package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sony.banteriorprototype.data.InteriorData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-25.
 */
public class DetailInteriorAdapter extends BaseAdapter {
    List<InteriorData> items = new ArrayList<>();

    public void add(InteriorData data){
        items.add(data);
    }
    @Override
    public int getCount() {
        return items.size();
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
        DetailInteriorView view;
        if(convertView == null){
            view = new DetailInteriorView(parent.getContext());
        }
        else {
            view = (DetailInteriorView)convertView;
        }
        view.setDetailInteriorView(items.get(position));
        return view;
    }
}
