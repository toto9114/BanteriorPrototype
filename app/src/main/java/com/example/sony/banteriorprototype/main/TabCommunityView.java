package com.example.sony.banteriorprototype.main;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.sony.banteriorprototype.R;

/**
 * Created by sony on 2016-03-10.
 */
public class TabCommunityView extends FrameLayout {
    ImageView tabView;
    public TabCommunityView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_tab_community, this);
        tabView = (ImageView)findViewById(R.id.image_tab_community);
    }
}
