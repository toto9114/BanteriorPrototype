package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    ImageView productView;
    TextView idView,nameView,sizeView;

    private void init(){
        inflate(getContext(), R.layout.view_product,this);
        productView = (ImageView)findViewById(R.id.image_product);
        idView = (TextView)findViewById(R.id.text_id);
        nameView = (TextView)findViewById(R.id.text_name);
        sizeView = (TextView)findViewById(R.id.text_size);
    }

    ProductData data;
    public void setProduct(ProductData data){
        this.data = data;
        productView.setImageResource(data.productImage);
        idView.setText(data.productId);
        nameView.setText(data.name);
        sizeView.setText(data.size);
    }
}
