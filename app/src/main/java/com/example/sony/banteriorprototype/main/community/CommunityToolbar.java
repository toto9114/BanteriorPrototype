package com.example.sony.banteriorprototype.main.community;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;

/**
 * Created by sony on 2016-02-25.
 */
public class CommunityToolbar extends FrameLayout {
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
            public void onClick(View v) {
                if (popup == null) {
                    popup = new CommunityPopupWindow(getContext());
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
    Button menuBtn;
    private void init(){
        inflate(getContext(), R.layout.view_community_toolbar,this);
        profileView = (ImageView)findViewById(R.id.image_profile);
        nameView = (TextView)findViewById(R.id.text_name);
        menuBtn = (Button)findViewById(R.id.btn_menu);
    }

    public void setToolbar(){

    }
}
