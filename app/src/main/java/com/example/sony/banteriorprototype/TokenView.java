package com.example.sony.banteriorprototype;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by sony on 2016-03-16.
 */
public class TokenView extends FrameLayout {
    TextView textView;
    public TokenView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_token, this);
        textView = (TextView)findViewById(R.id.text_token);
    }
    public void setToken(String s){
        textView.setText(s);
    }

}
