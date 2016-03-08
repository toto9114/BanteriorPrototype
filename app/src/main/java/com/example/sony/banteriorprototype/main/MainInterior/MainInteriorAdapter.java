package com.example.sony.banteriorprototype.main.MainInterior;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;
import com.example.sony.banteriorprototype.data.Interior.InteriorData;
import com.example.sony.banteriorprototype.data.MainPage.MainContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-23.
 */
public class MainInteriorAdapter extends BaseAdapter {
    List<MainContent> items = new ArrayList<MainContent>();
    @Override
    public int getCount() {
        return items.size();
    }

    public void addMainInterior(MainContent data){
        items.add(data);
        notifyDataSetChanged();
    }
//    public void addAll(List<InteriorData> data){
//        items.addAll(data);
//        notifyDataSetChanged();
//    }

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
