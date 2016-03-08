package com.example.sony.banteriorprototype.main.MainInterior;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;
import com.example.sony.banteriorprototype.data.Interior.InteriorData;
import com.example.sony.banteriorprototype.data.MainPage.MainContent;

/**
 * Created by sony on 2016-02-23.
 */
public class MainInteriorView extends FrameLayout {
    ImageView interiorView;
    TextView titleView;
    TextView descriptionView;
    TextView postCountView, scrapView;
    public MainInteriorView(Context context) {
        super(context);
        init();
    }
    private void init(){
        inflate(getContext(), R.layout.view_main_interior,this);
        interiorView = (ImageView)findViewById(R.id.image_interior);
        titleView = (TextView)findViewById(R.id.text_title);
        descriptionView = (TextView)findViewById(R.id.text_description);
        postCountView = (TextView)findViewById(R.id.text_post_count);
        scrapView = (TextView)findViewById(R.id.text_scrap_count);
    }
    MainContent data;
    public void setMainInteriorData(MainContent data){
        this.data = data;
        Glide.with(getContext()).load(data.file_url).into(interiorView);
        titleView.setText(data.category);
        descriptionView.setText(data.content);
        postCountView.setText(data.scrap_count);
        scrapView.setText(data.post_count);
    }

}
