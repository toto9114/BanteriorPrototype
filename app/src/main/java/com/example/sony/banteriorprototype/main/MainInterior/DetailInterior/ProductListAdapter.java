package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sony.banteriorprototype.data.ProductData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-23.
 */
public class ProductListAdapter extends BaseAdapter implements OnProductItemClickListener{
    List<ProductData> items = new ArrayList<>();

    public void add(ProductData data){
        items.add(data);
        notifyDataSetChanged();
    }
    public void addAll(List<ProductData> list){
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
        ProductView view;
        if(convertView == null){
            view = new ProductView(parent.getContext());
        }
        else {
            view = (ProductView)convertView;
        }
        view.setProduct(items.get(position));
        view.setOnItemClickListener(this);
        return view;
    }

    OnProductItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnProductItemClickListener listener){
        onItemClickListener = listener;
    }

    @Override
    public void onItemClick(View view, ProductData data) {
        if(onItemClickListener != null){
            onItemClickListener.onItemClick(view,data);
        }
    }
}
