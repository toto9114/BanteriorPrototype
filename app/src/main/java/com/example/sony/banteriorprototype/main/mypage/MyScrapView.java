package com.example.sony.banteriorprototype.main.mypage;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Mypage.MyPostData;
import com.example.sony.banteriorprototype.data.Mypage.ScrapData;
import com.wefika.flowlayout.FlowLayout;

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
    TextView scrapView;
    FlowLayout mFlowLayout;

    private void init() {
        inflate(getContext(), R.layout.view_thumb, this);
        thumbView = (ImageView) findViewById(R.id.image_thumb);
        profileView = (ImageView) findViewById(R.id.image_profile);
        // tagView = (TextView) findViewById(R.id.text_hash_tag);
        mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        scrapView = (TextView) findViewById(R.id.text_scrap_count);
    }

    MyPostData myPost;

    public void setMyPostData(MyPostData info) {
        myPost = info;
        Glide.with(getContext()).load(myPost.interiorImage).into(thumbView);
        Glide.with(getContext()).load(myPost.profileImage).into(profileView);

        mFlowLayout.removeAllViews();

        for (int i = 0; i < info.hash_tag.size(); i++) {
            TextView hashTag = new TextView(getContext());
            hashTag.setTextColor(Color.WHITE);
            hashTag.setText(info.hash_tag.get(i) + " ");
            mFlowLayout.addView(hashTag, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }


    ScrapData data;

    public void setScrapData(ScrapData data) {
        this.data = data;
        Glide.with(getContext()).load(data.interiorImage).into(thumbView);
        if (data.profileImage != null) {
            Glide.with(getContext()).load(data.profileImage).into(profileView);
        }
        mFlowLayout.removeAllViews();
        for (int i = 0; i < data.hash_tag.size(); i++) {
            TextView hashTag = new TextView(getContext());
            hashTag.setTextColor(Color.WHITE);
            hashTag.setText(data.hash_tag.get(i) + " ");
            mFlowLayout.addView(hashTag, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
