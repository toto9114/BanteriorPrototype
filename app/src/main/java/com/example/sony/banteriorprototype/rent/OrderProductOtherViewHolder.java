package com.example.sony.banteriorprototype.rent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;

/**
 * Created by sony on 2016-03-02.
 */
public class OrderProductOtherViewHolder extends RecyclerView.ViewHolder{

    ImageView addressIconView;
    TextView addressTitleView;

    ImageView rentIconView;
    TextView rentTitleView;

    ImageView totalIconView;
    TextView totalTitleView;

    public OrderProductOtherViewHolder(View itemView) {
        super(itemView);
        addressIconView = (ImageView)itemView.findViewById(R.id.image_address_icon);
        addressTitleView = (TextView)itemView.findViewById(R.id.text_address_title);

        rentIconView = (ImageView)itemView.findViewById(R.id.image_rent_icon);
        rentTitleView = (TextView)itemView.findViewById(R.id.text_rent_title);

        totalIconView = (ImageView)itemView.findViewById(R.id.image_total_icon);
        totalTitleView = (TextView)itemView.findViewById(R.id.text_total_title);
    }
    public void setAddress(){
        addressIconView.setImageResource(R.mipmap.ic_launcher);
        addressTitleView.setText(R.string.header_address);

        rentIconView.setImageResource(R.mipmap.ic_launcher);
        rentTitleView.setText(R.string.header_rent);

        totalIconView.setImageResource(R.mipmap.ic_launcher);
        totalTitleView.setText(R.string.header_total);
    }

}
