package com.example.sony.banteriorprototype.rent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.AddressInfo;
import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;
import com.example.sony.banteriorprototype.data.ProductData;
import com.example.sony.banteriorprototype.main.community.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-03-02.
 */
public class OrderAdapter extends RecyclerView.Adapter implements OnItemClickListener{
    List<ProductData> items = new ArrayList<>();

    private static final int TITILE_COUNT = 1;
    private static final int HEADER_COUNT = 1;
    private static final int FOOTER_COUNT = 1;

    private static final int VIEW_PRODUCT_HEADER = 0;
    private static final int VIEW_PRODUCT_ITEM = 100;
    private static final int VIEW_FOOTER = 200;
    private static final int VIEW_TITLE = 300;
    OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public void onItemClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(view);
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    int year, month;

    public void setDate(int year, int month) {
        this.year = year;
        this.month = month;
        notifyDataSetChanged();
    }

    public void add(ProductData data) {
        items.add(data);
        notifyDataSetChanged();
    }

    public void addAll(List<ProductData> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    InteriorContentData interiorContentData;

    public void setInterior(InteriorContentData interiorContentData) {
        this.interiorContentData = interiorContentData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < TITILE_COUNT) {
            return VIEW_TITLE + position;
        } else if (position < TITILE_COUNT + HEADER_COUNT) {
            return VIEW_PRODUCT_HEADER;
        } else if (position < items.size() + HEADER_COUNT + TITILE_COUNT) {
            return VIEW_PRODUCT_ITEM;
        } else {
            return VIEW_FOOTER;
        }
    }

    View headerView, footerView;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TITLE:
                View title = inflater.inflate(R.layout.view_order_product_title, parent, false);
                return new OrderProductTitleViewHolder(title);
            case VIEW_PRODUCT_HEADER:
                if(headerView == null) {
                    headerView = inflater.inflate(R.layout.view_header_product, parent, false);
                }
                return new OrderProductHeaderViewHolder(headerView);
            case VIEW_PRODUCT_ITEM:
                View item = inflater.inflate(R.layout.view_order_product_list, parent, false);
                return new OrderProductViewHolder(item);
            case VIEW_FOOTER:
                if (footerView == null) {
                    footerView = inflater.inflate(R.layout.view_order_other, parent, false);
                }
                OrderProductOtherViewHolder holder = new OrderProductOtherViewHolder(footerView);
                info = holder.getAddress();
                holder.setOnItemClickListener(this);
                return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int index = position;
        if (position >= HEADER_COUNT + 1) {
            index = position - HEADER_COUNT - 1;
        }
        switch (getItemViewType(position)) {
            case VIEW_TITLE:
                ((OrderProductTitleViewHolder) holder).setTitle(interiorContentData);
                return;
            case VIEW_PRODUCT_HEADER:
                ((OrderProductHeaderViewHolder) holder).setHeader();
                return;
            case VIEW_PRODUCT_ITEM:
                ((OrderProductViewHolder) holder).setData(items.get(index));
                return;
            case VIEW_FOOTER:
                ((OrderProductOtherViewHolder) holder).setAddress();
                ((OrderProductOtherViewHolder) holder).setDate(year, month);
                return;
        }
    }


    AddressInfo info;
    @Override
    public int getItemCount() {
        return HEADER_COUNT + items.size() + FOOTER_COUNT + 1;
    }
}
