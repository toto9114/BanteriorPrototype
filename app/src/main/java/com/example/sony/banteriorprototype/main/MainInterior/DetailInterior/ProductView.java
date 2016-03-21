package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.ProductData;

/**
 * Created by sony on 2016-02-24.
 */
public class ProductView extends FrameLayout {
    public ProductView(Context context) {
        super(context);
        init();
    }

    OnProductItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnProductItemClickListener listener){
        onItemClickListener = listener;
    }
    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        siteLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(v,data);
                }
            }
        });
    }

    ImageView productView;
    TextView brandView,idView,nameView,sizeView;
    ImageView colorView;
    Button siteLink;
    private void init(){
        inflate(getContext(), R.layout.view_product,this);
        productView = (ImageView)findViewById(R.id.image_product);
        brandView = (TextView)findViewById(R.id.text_brand);
        idView = (TextView)findViewById(R.id.text_id);
        nameView = (TextView)findViewById(R.id.text_brand);
        sizeView = (TextView)findViewById(R.id.text_size);
        colorView = (ImageView)findViewById(R.id.image_color_1);
        siteLink = (Button)findViewById(R.id.btn_link);
    }

    ProductData data;
    public void setProduct(ProductData data){
        this.data = data;
        Glide.with(getContext()).load(data.productImage).into(productView);
        brandView.setText(data.brand);
        idView.setText(data.productId);
        nameView.setText(data.name);
        sizeView.setText(data.size);
        colorView.setBackgroundColor(Integer.parseInt(data.color));
    }
}
