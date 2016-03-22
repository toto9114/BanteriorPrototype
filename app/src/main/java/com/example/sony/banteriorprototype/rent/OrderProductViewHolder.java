package com.example.sony.banteriorprototype.rent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.MyApplication;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.ProductData;

/**
 * Created by sony on 2016-03-02.
 */
public class OrderProductViewHolder extends RecyclerView.ViewHolder {
    ImageView productView;
    TextView brandView,nameView;
    ImageView colorView;

    TextView priceView;

    public OrderProductViewHolder(View itemView) {
        super(itemView);
        productView = (ImageView)itemView.findViewById(R.id.image_product);
        brandView = (TextView)itemView.findViewById(R.id.text_brand);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        colorView = (ImageView)itemView.findViewById(R.id.image_color);
        priceView = (TextView)itemView.findViewById(R.id.text_price);
    }

    ProductData data;
    public void setData(ProductData data){
        this.data = data;
        Glide.with(MyApplication.getContext()).load(data.productImage).into(productView);
        brandView.setText(data.brand);
        nameView.setText(data.name);
        colorView.setBackgroundColor(Integer.parseInt(data.color));
        priceView.setText(data.price);
    }

}
