package com.example.sony.banteriorprototype.main.community;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sony.banteriorprototype.data.CommunityData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-23.
 */
public class CommunityAdapter extends BaseAdapter {
    List<CommunityData> items = new ArrayList<CommunityData>();

    public void add(CommunityData data){
        items.add(data);
        notifyDataSetChanged();
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
        ThumbnailView view;
        if(convertView == null){
            view = new ThumbnailView(parent.getContext());
        }
        else {
            view = (ThumbnailView)convertView;
        }
        view.setCommunityThumbnailData(items.get(position));
        return view;
    }
}