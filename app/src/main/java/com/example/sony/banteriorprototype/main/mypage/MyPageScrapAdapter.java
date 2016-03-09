package com.example.sony.banteriorprototype.main.mypage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sony.banteriorprototype.data.Mypage.ScrapData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-27.
 */
public class MyPageScrapAdapter extends BaseAdapter {
    List<ScrapData> items = new ArrayList<>();

    public void add(ScrapData data){
        items.add(data);
        notifyDataSetChanged();
    }
    public void addAll(List<ScrapData> list){
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
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
        MyScrapView view;
        if(convertView == null){
            view = new MyScrapView(parent.getContext());
        }
        else {
            view = (MyScrapView)convertView;
        }
        view.setScrapData(items.get(position));
        return view;
    }
}
