package com.example.sony.banteriorprototype.main.mypage;


import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.NotificationData;

/**
 * Created by sony on 2016-02-23.
 */
public class NotifyView extends FrameLayout {
    public NotifyView(Context context) {
        super(context);
        init();
    }

    ImageView iconView;
    TextView messageView;
    public void init(){
        inflate(getContext(), R.layout.view_notify,this);
        iconView = (ImageView)findViewById(R.id.image_icon);
        messageView = (TextView)findViewById(R.id.text_message);
    }
    NotificationData data;
    public void setNotify(NotificationData data){
        this.data = data;
        iconView.setImageResource(data.notifyIcon);
        messageView.setText(data.notification);
    }
}
