package com.example.sony.banteriorprototype.main.community;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.CommunityData;
import com.example.sony.banteriorprototype.data.InteriorData;

/**
 * Created by sony on 2016-02-23.
 */
public class ThumbnailView extends FrameLayout {
    public ThumbnailView(Context context) {
        super(context);
        init();
    }
    ImageView thumbView;
    ImageView profileView;
    private void init(){
        inflate(getContext(), R.layout.view_community_thumb,this);
        thumbView = (ImageView)findViewById(R.id.image_thumb);
        profileView = (ImageView)findViewById(R.id.image_profile);
    }
    CommunityData data;
    public void setThumbnailData(CommunityData data){
        this.data = data;
        thumbView.setImageResource(data.mainImage);
        profileView.setImageResource(data.profileImage);
    }
}
