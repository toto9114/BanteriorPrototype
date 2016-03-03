package com.example.sony.banteriorprototype.main.community;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.CommentData;
import com.example.sony.banteriorprototype.data.CommunityContentData;

/**
 * Created by sony on 2016-02-26.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {
    TextView nameView;
    TextView commentView;

    public CommentViewHolder(View itemView) {
        super(itemView);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        commentView = (TextView)itemView.findViewById(R.id.text_comment);
    }
    CommentData data;
    public void setComment(CommentData data){
        this.data = data;
        nameView.setText(data.name);
        commentView.setText(data.comment);
    }
}
