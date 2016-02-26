package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.InteriorData;

/**
 * Created by sony on 2016-02-25.
 */
public class DetailInteriorView extends FrameLayout {
    public DetailInteriorView(Context context) {
        super(context);
        init();
    }
    ImageView interiorView;
    TextView hashTagView;
    private void init(){
        inflate(getContext(), R.layout.view_detail_interior,this);
        interiorView = (ImageView)findViewById(R.id.image_detail_interior);
       // hashTagView = (TextView)findViewById(R.id.text_hash_1);
    }
    public void setDetailInteriorView(InteriorData data){
        interiorView.setImageResource(data.interiorImage);
    }
}
