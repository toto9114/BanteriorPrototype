package com.example.sony.banteriorprototype.main.community;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Community.CommunityContentData;

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
    CommunityContentData communityData;
    public void setCommunityThumbnailData(CommunityContentData data){
        this.communityData = data;
        Glide.with(getContext()).load(data.mainImage).into(thumbView);
        Glide.with(getContext()).load(data.profileImage).into(profileView);
    }

}
