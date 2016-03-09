package com.example.sony.banteriorprototype.main.community;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.CommentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-26.
 */
public class CommentAdapter extends RecyclerView.Adapter {
    List<CommentData> items = new ArrayList<>();

    public void add(CommentData data){
        items.add(data);
        notifyDataSetChanged();
    }
    public void addAll(List<CommentData> list){
        items.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_comment,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CommentViewHolder)holder).setComment(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
