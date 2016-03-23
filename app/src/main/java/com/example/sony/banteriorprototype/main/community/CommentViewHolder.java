package com.example.sony.banteriorprototype.main.community;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.CommentData;

/**
 * Created by sony on 2016-02-26.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {

    public OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    TextView nameView;
    TextView commentView;
    Button delButton;
    Context mContext;
    boolean isMyPost =false;
    public CommentViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        commentView = (TextView)itemView.findViewById(R.id.text_comment);
        delButton = (Button)itemView.findViewById(R.id.btn_delete);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        });
    }
    CommentData data;
    public void setComment(CommentData data,boolean isMyPost){
        this.data = data;
        nameView.setText(data.username);
        commentView.setText(data.reply_content);
        if(!isMyPost){
            delButton.setVisibility(View.GONE);
        }else {
            delButton.setVisibility(View.VISIBLE);
        }
    }

}
