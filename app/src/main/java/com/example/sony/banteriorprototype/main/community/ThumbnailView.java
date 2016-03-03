package com.example.sony.banteriorprototype.main.community;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.CommunityData;

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
        inflate(getContext(), R.layout.view_thumb,this);
        thumbView = (ImageView)findViewById(R.id.image_thumb);
        profileView = (ImageView)findViewById(R.id.image_profile);
    }
    CommunityData communityData;
    public void setCommunityThumbnailData(CommunityData data){
        this.communityData = data;
        thumbView.setImageResource(data.mainImage);
        profileView.setImageResource(data.profileImage);
    }

}
