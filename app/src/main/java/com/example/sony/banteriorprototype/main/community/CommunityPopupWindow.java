package com.example.sony.banteriorprototype.main.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.sony.banteriorprototype.R;

/**
 * Created by sony on 2016-02-25.
 */
public class CommunityPopupWindow extends PopupWindow {
    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public CommunityPopupWindow(Context context){
        super(context);

        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setOutsideTouchable(true);

        View view = LayoutInflater.from(context).inflate(R.layout.popup_community,null);
        Button btn = (Button)view.findViewById(R.id.btn_edit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClick(v);
                }
                dismiss();
            }
        });
        btn = (Button)view.findViewById(R.id.btn_delete);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!= null){
                    mListener.onItemClick(v);
                }
                dismiss();
            }
        });
        setContentView(view);
    }
}
