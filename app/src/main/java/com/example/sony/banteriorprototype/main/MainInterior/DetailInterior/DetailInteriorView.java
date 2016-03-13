package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;

/**
 * Created by sony on 2016-02-25.
 */
public class DetailInteriorView extends FrameLayout {
    public DetailInteriorView(Context context) {
        super(context);
        init();
    }
    ImageView interiorView;
    ImageView profileView;
    TextView hashTagView;
    private void init(){
        inflate(getContext(), R.layout.view_detail_interior,this);
        interiorView = (ImageView)findViewById(R.id.image_detail_interior);
        profileView = (ImageView)findViewById(R.id.image_profile);
       // hashTagView = (TextView)findViewById(R.id.text_hash_1);
    }
    public void setDetailInteriorView(InteriorContentData data){
        Glide.with(getContext()).load(data.interiorImage).into(interiorView);
        //Glide.with(getContext()).load(data.profileImage).into(profileView);
        profileView.setImageResource(R.drawable.ic_line_logo);
    }
}
