package com.example.sony.banteriorprototype.main.community;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sony.banteriorprototype.GCM.PropertyManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.CommentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sony on 2016-02-26.
 */
public class CommentAdapter extends RecyclerView.Adapter implements OnItemClickListener{
    List<CommentData> items = new ArrayList<>();

    public void add(CommentData data){
        items.add(data);
        notifyDataSetChanged();
    }
    public void addAll(List<CommentData> list){
        items.addAll(list);
        notifyDataSetChanged();
    }
    public void clear(){
        items.clear();
    }

    public CommentData getComment(int position){
        return items.get(position);
    }
    OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    @Override
    public void onItemClick(View view) {

    }

    @Override
    public void onItemClick(View view, int position) {
        if(itemClickListener !=null){
            itemClickListener.onItemClick(view,position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_comment, parent, false);
        CommentViewHolder holder = new CommentViewHolder(view);
        holder.setOnItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int id = PropertyManager.getInstance().getId();
        CommentData data = items.get(position);
        ((CommentViewHolder) holder).setComment(data, data.userid==id);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
