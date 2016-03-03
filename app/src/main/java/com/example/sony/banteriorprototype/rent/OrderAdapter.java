package com.example.sony.banteriorprototype.rent;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.ProductData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-03-02.
 */
public class OrderAdapter extends RecyclerView.Adapter {
    List<ProductData> items = new ArrayList<>();

    private static final int HEADER_COUNT= 1;
    private static final int FOOTER_COUNT =1;

    private static final int VIEW_PRODUCT_HEADER = 0;
    private static final int VIEW_PRODUCT_ITEM =100;
    private static final int VIEW_FOOTER =200;

    public void add(ProductData data){
        items.add(data);
        Log.i("OrderAdapter:",""+items.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position<HEADER_COUNT){
            return VIEW_PRODUCT_HEADER+position;
        }
        else if(position<items.size()+HEADER_COUNT){
            return VIEW_PRODUCT_ITEM;
        }
        else {
            return VIEW_FOOTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case VIEW_PRODUCT_HEADER:
                View header = inflater.inflate(R.layout.view_header_product,parent,false);
                return new OrderProductHeaderViewHolder(header);
            case VIEW_PRODUCT_ITEM:
                View item = inflater.inflate(R.layout.view_order_product_list,parent,false);
                return new OrderProductViewHolder(item);
            case VIEW_FOOTER:
                View footer = inflater.inflate(R.layout.view_order_other,parent,false);
                return new OrderProductOtherViewHolder(footer);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int index = position;
        if(position>=HEADER_COUNT){
            index = position-HEADER_COUNT;
        }
        switch (getItemViewType(position)){
           case VIEW_PRODUCT_HEADER:
               ((OrderProductHeaderViewHolder)holder).setHeader();
               return;
            case VIEW_PRODUCT_ITEM:
                ((OrderProductViewHolder)holder).setData(items.get(index));
                return;
            case VIEW_FOOTER:
                ((OrderProductOtherViewHolder)holder).setAddress();
                return;
        }
    }

    @Override
    public int getItemCount() {
        return HEADER_COUNT+items.size()+FOOTER_COUNT;
    }
}
