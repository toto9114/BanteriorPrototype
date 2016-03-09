package com.example.sony.banteriorprototype.rent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;

/**
 * Created by sony on 2016-03-09.
 */
public class OrderProductTitleViewHolder extends RecyclerView.ViewHolder {
    ImageView interiorView;
    TextView packageView;
    TextView monthPriceView;
    Context mContext;

    public OrderProductTitleViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        interiorView = (ImageView)itemView.findViewById(R.id.image_interior);
        packageView = (TextView)itemView.findViewById(R.id.text_package);
        monthPriceView = (TextView)itemView.findViewById(R.id.text_month_price);
    }
    InteriorContentData data;
    public void setTitle(InteriorContentData data){
        this.data = data;
//        Glide.with(mContext).load(data.interiorImage).into(interiorView);
//        packageView.setText(data.);
//        monthPriceView.setText();
    }
}
