package com.example.sony.banteriorprototype.main.mypage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sony.banteriorprototype.data.NotificationData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-23.
 */
public class NotifyAdapter extends BaseAdapter {
    List<NotificationData> items = new ArrayList<NotificationData>();

    public void add(NotificationData data){
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
        NotifyView view;
        if(convertView == null){
            view = new NotifyView(parent.getContext());
        }
        else {
            view = (NotifyView)convertView;
        }
        view.setNotify(items.get(position));
        return view;
    }
}
