package com.example.sony.banteriorprototype.main.community;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.sony.banteriorprototype.R;

/**
 * Created by sony on 2016-02-26.
 */
public class FloatingView extends FrameLayout {
    public FloatingView(Context context) {
        super(context);
    }

    public FloatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        inflate(getContext(), R.layout.view_floating,this);
        Button btn = (Button)findViewById(R.id.btn_camera);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"camera",Toast.LENGTH_SHORT).show();
            }
        });
        btn = (Button)findViewById(R.id.btn_gallery);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"gallery",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
