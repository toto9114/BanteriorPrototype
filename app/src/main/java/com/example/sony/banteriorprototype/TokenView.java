package com.example.sony.banteriorprototype;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sony.banteriorprototype.main.community.OnItemClickListener;

/**
 * Created by sony on 2016-03-16.
 */
public class TokenView extends FrameLayout implements OnItemClickListener{
    TextView textView;
    public TokenView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_token, this);
        textView = (TextView)findViewById(R.id.text_token);
    }
    public void setToken(String s){
        textView.setText(s);
    }

    OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }
    @Override
    public void onItemClick(View view) {

    }

    @Override
    public void onItemClick(View view, int position) {
        if(onItemClickListener !=null){
            onItemClickListener.onItemClick(view,position);
        }
    }
}
