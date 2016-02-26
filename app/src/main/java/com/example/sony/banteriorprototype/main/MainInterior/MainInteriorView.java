package com.example.sony.banteriorprototype.main.MainInterior;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.InteriorData;

/**
 * Created by sony on 2016-02-23.
 */
public class MainInteriorView extends FrameLayout {
    ImageView interiorView;
    TextView titleView;
    TextView descriptionView;
    public MainInteriorView(Context context) {
        super(context);
        init();
    }
    private void init(){
        inflate(getContext(), R.layout.view_main_interior,this);
        interiorView = (ImageView)findViewById(R.id.image_interior);
        titleView = (TextView)findViewById(R.id.text_title);
        descriptionView = (TextView)findViewById(R.id.text_description);
    }
    InteriorData data;
    public void setMainInteriorData(InteriorData data){
        this.data = data;
        interiorView.setImageResource(data.interiorImage);
        titleView.setText("Title..");
        descriptionView.setText("desc...");
    }

}
