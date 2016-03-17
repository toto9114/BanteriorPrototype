package com.example.sony.banteriorprototype.main.community;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Community.CommunityContentData;
import com.example.sony.banteriorprototype.data.Search.SearchContentData;
import com.wefika.flowlayout.FlowLayout;

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
    TextView hashTagView,scrapView;
    FlowLayout mFlowLayout;
    private void init(){
        inflate(getContext(), R.layout.view_thumb,this);
        thumbView = (ImageView)findViewById(R.id.image_thumb);
        profileView = (ImageView)findViewById(R.id.image_profile);
        scrapView = (TextView)findViewById(R.id.text_scrap_count);
//        hashTagView = (TextView)findViewById(R.id.text_hash_tag);
        mFlowLayout = (FlowLayout)findViewById(R.id.flow_layout);
    }
    CommunityContentData communityData;
    public void setCommunityThumbnailData(CommunityContentData data){
        this.communityData = data;
        Glide.with(getContext()).load(data.mainImage).into(thumbView);
        Glide.with(getContext()).load(data.profileImage).into(profileView);
        scrapView.setText("" + data.scrap_count);
        mFlowLayout.removeAllViews();
        for(int i =0; i< data.hash_tag.size();i++){
            TextView hashTag = new TextView(getContext());
            hashTag.setTextColor(Color.WHITE);
            hashTag.setText(data.hash_tag.get(i) + " ");
            mFlowLayout.addView(hashTag, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

//        StringBuilder sb = new StringBuilder();
//        for(String s : data.hash_tag){
//            sb.append(s);
//            sb.append(" ");
//        }
//        hashTagView.setText(sb.toString());
    }
    SearchContentData searchContentData;
    public void setResultData(SearchContentData data){
        searchContentData = data;
        Glide.with(getContext()).load(data.interiorImage).into(thumbView);
        if(data.profileImage != null) {
            Glide.with(getContext()).load(data.profileImage).into(profileView);
        }

        scrapView.setText(""+data.scrap_count);
        mFlowLayout.removeAllViews();
        for(int i =0; i< data.hash_tag.size();i++){
            TextView hashTag = new TextView(getContext());
            hashTag.setTextColor(Color.WHITE);
            hashTag.setText(data.hash_tag.get(i)+" ");
            mFlowLayout.addView(hashTag, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
