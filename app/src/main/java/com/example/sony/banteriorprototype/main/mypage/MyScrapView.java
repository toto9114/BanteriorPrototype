package com.example.sony.banteriorprototype.main.mypage;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Mypage.MyPostData;
import com.example.sony.banteriorprototype.data.Mypage.ScrapData;

/**
 * Created by sony on 2016-02-27.
 */
public class MyScrapView extends FrameLayout {
    public MyScrapView(Context context) {
        super(context);
        init();
    }

    ImageView thumbView;
    ImageView profileView;
    TextView tagView,scrapView;

    private void init() {
        inflate(getContext(), R.layout.view_thumb, this);
        thumbView = (ImageView) findViewById(R.id.image_thumb);
        profileView = (ImageView) findViewById(R.id.image_profile);
        tagView = (TextView) findViewById(R.id.text_hash_tag);
        scrapView = (TextView)findViewById(R.id.text_scrap_count);
    }

    MyPostData myPost;

    public void setMyPostData(MyPostData info) {
        myPost = info;
        Glide.with(getContext()).load(myPost.interiorImage).into(thumbView);
        Glide.with(getContext()).load(myPost.profileImage).into(profileView);
        tagView.setText(myPost.hash_tag);
    }


    ScrapData data;
    public void setScrapData(ScrapData data) {
        this.data = data;
        Glide.with(getContext()).load(data.interiorImage).into(thumbView);
        Glide.with(getContext()).load(data.profileImage).into(profileView);
        tagView.setText(data.hash_tag);
    }
}
