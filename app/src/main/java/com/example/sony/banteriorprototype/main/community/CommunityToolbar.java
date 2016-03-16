package com.example.sony.banteriorprototype.main.community;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Community.CommunityContentData;

/**
 * Created by sony on 2016-02-25.
 */
public class CommunityToolbar extends FrameLayout implements OnItemClickListener{
    public CommunityToolbar(Context context) {
        super(context);
        init();
    }

    CommunityPopupWindow popup;

    boolean isShowing = false;

    public CommunityToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        menuBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (popup == null) {
                    popup = new CommunityPopupWindow(getContext());
                    popup.setOnItemClickListener(CommunityToolbar.this);
                    popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            isShowing = false;
                        }
                    });
                }
                if (isShowing) {
                    popup.dismiss();
                } else {
                    popup.showAsDropDown(v);
                    isShowing = true;
                }
            }
        });
    }

    ImageView profileView;
    TextView nameView;
    ImageView menuBtn;

    private void init() {
        inflate(getContext(), R.layout.view_community_toolbar, this);
        profileView = (ImageView) findViewById(R.id.image_profile);
        nameView = (TextView) findViewById(R.id.text_brand);
        menuBtn = (ImageView) findViewById(R.id.btn_menu);
    }

    public void setToolbar(CommunityContentData data){
        Glide.with(getContext()).load(data.profileImage).into(profileView);
        nameView.setText(data.username);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    @Override
    public void onItemClick(View view) {
        if(mListener!=null){
            mListener.onItemClick(view);
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
