package com.example.sony.banteriorprototype.rent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.AddressInfo;
import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;
import com.example.sony.banteriorprototype.data.ProductData;
import com.example.sony.banteriorprototype.main.community.OnItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sony on 2016-03-02.
 */
public class OrderAdapter extends RecyclerView.Adapter implements OnItemClickListener {
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
    public InteriorContentData getInterior(){
        return  this.interiorContentData;
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
                if (headerView == null) {
                    headerView = inflater.inflate(R.layout.view_header_product, parent, false);
                }
                return new OrderProductHeaderViewHolder(headerView);
            case VIEW_PRODUCT_ITEM:
                View item = inflater.inflate(R.layout.view_order_product_list, parent, false);
                return new OrderProductViewHolder(item);
            case VIEW_FOOTER:
                if (footerView == null) {
                    footerView = inflater.inflate(R.layout.view_order_other, parent, false);
                    phoneView = (EditText)footerView.findViewById(R.id.edit_phone);
                    DetailAddressView = (EditText)footerView.findViewById(R.id.edit_detail_address);
                    addressView1 = (EditText) footerView.findViewById(R.id.edit_adderess1);
                    addressView2 = (EditText) footerView.findViewById(R.id.edit_address2);
                    yearView = (EditText) footerView.findViewById(R.id.edit_year);
                    monthView = (EditText) footerView.findViewById(R.id.edit_month);
                    cardBtn = (Button) footerView.findViewById(R.id.btn_card);
                    phoneBtn = (Button) footerView.findViewById(R.id.btn_phone);
                    checkBtn = (Button) footerView.findViewById(R.id.btn_check);
                }
                OrderProductOtherViewHolder holder = new OrderProductOtherViewHolder(footerView);
                holder.setOnItemClickListener(this);
                return holder;
        }
        return null;
    }

    EditText phoneView, addressView1, addressView2, DetailAddressView, yearView, monthView;
    Button cardBtn;
    Button phoneBtn;
    Button checkBtn;

    private static final String[] method = {"카드", "폰", "무통장입금"};

    public AddressInfo getAddress() {
        AddressInfo info = new AddressInfo();
        if (footerView == null) {
            return null;
        }
        info.address = DetailAddressView.getText().toString();
        info.phone = phoneView.getText().toString();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int diff = (this.year - year) * 12 + (this.month - month);
        info.period = diff;
        if(cardBtn.isSelected()) {
            info.paymethod = method[0];
        }else if(phoneBtn.isSelected()){
            info.paymethod = method[1];
        }else {
            info.paymethod = method[2];
        }
        return info;
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
                ((OrderProductOtherViewHolder) holder).setDate(year, month);
                ((OrderProductOtherViewHolder) holder).setPrice(price);
                return;
        }
    }
    int price;
    public void setPrice(int price){
        this.price = price;
    }
    @Override
    public int getItemCount() {
        return HEADER_COUNT + items.size() + FOOTER_COUNT + 1;
    }
}
