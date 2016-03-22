package com.example.sony.banteriorprototype.rent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;

/**
 * Created by sony on 2016-03-02.
 */
public class OrderProductHeaderViewHolder extends RecyclerView.ViewHolder {
    ImageView iconView;
    TextView titleView;
    public OrderProductHeaderViewHolder(View itemView) {
        super(itemView);
        iconView = (ImageView)itemView.findViewById(R.id.image_address_icon);
        titleView = (TextView)itemView.findViewById(R.id.text_title);
    }
    public void setHeader(){
        titleView.setText("주문상품정보");
    }
}
