package com.example.sony.banteriorprototype.main.search;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sony.banteriorprototype.data.Search.SearchContentData;
import com.example.sony.banteriorprototype.main.community.ThumbnailView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-03-05.
 */
public class SearchResultAdapter extends BaseAdapter {
    List<SearchContentData> items = new ArrayList<>();

    public void addAll(List<SearchContentData> data) {
        items.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    int page=1;
    public void setCurrentPage(int page){
        this.page = page;
    }
    public int getCurrentPage(){
        return page;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
        if (convertView == null) {
            view = new ThumbnailView(parent.getContext());
        } else {
            view = (ThumbnailView) convertView;
        }
        view.setResultData(items.get(position));
        return view;
    }
}
