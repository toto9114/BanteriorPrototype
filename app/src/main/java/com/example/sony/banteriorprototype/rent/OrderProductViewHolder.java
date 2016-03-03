package com.example.sony.banteriorprototype.rent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.ProductData;

/**
 * Created by sony on 2016-03-02.
 */
public class OrderProductViewHolder extends RecyclerView.ViewHolder {
    ImageView productView;
    TextView nameView;
    ImageView colorView;
    TextView colorNameView;
    TextView priceView;

    public OrderProductViewHolder(View itemView) {
        super(itemView);
        productView = (ImageView)itemView.findViewById(R.id.image_product);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        colorView = (ImageView)itemView.findViewById(R.id.image_color);
        colorNameView = (TextView)itemView.findViewById(R.id.text_color);
        priceView = (TextView)itemView.findViewById(R.id.text_price);
    }

    ProductData data;
    public void setData(ProductData data){
        this.data = data;
        productView.setImageResource(data.productImage);
        nameView.setText(data.name);
    }
}
