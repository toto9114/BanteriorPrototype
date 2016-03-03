package com.example.sony.banteriorprototype.main.mypage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sony.banteriorprototype.data.Mypage.ThumbnailData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-27.
 */
public class MyPageAdapter extends BaseAdapter {
    List<ThumbnailData> items = new ArrayList<>();

    public void add(ThumbnailData data){
        items.add(data);
        notifyDataSetChanged();
    }
    public void addAll(List<ThumbnailData> list){
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
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
        MyScrapView view;
        if(convertView == null){
            view = new MyScrapView(parent.getContext());
        }
        else {
            view = (MyScrapView)convertView;
        }
        view.setThumb(items.get(position));
        return view;
    }
}
