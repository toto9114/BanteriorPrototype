package com.example.sony.banteriorprototype.main.mypage;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.MyWritingInfo;
import com.example.sony.banteriorprototype.data.ScrapData;
import com.example.sony.banteriorprototype.data.ThumbnailData;

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

    private void init(){
        inflate(getContext(), R.layout.view_thumb,this);
        thumbView = (ImageView)findViewById(R.id.image_thumb);
        profileView = (ImageView)findViewById(R.id.image_profile);
    }
//    ScrapData scrapData;
//    public void setScrapData(ScrapData data){
//        scrapData = data;
//        thumbView.setImageResource(data.interiorImage);
//        profileView.setImageResource(data.profileImage);
//    }
//    MyWritingInfo myWritingInfo;
//    public void setMyWritingInfo(MyWritingInfo info){
//        myWritingInfo = info;
//        thumbView.setImageResource(info.interiorImage);
//        profileView.setImageResource(info.profileImage);
//    }
    ThumbnailData data;
    public void setThumb(ThumbnailData data){
        if(data instanceof ScrapData){
            this.data = (ScrapData)data;
            thumbView.setImageResource(((ScrapData) data).interiorImage);
            profileView.setImageResource(((ScrapData) data).profileImage);
        }
        if(data instanceof MyWritingInfo){
            this.data = (MyWritingInfo)data;
            thumbView.setImageResource(((MyWritingInfo) data).interiorImage);
            profileView.setImageResource(((MyWritingInfo) data).profileImage);
        }
    }
}
