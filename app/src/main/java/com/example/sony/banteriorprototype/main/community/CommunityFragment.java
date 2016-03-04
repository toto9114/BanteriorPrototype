package com.example.sony.banteriorprototype.main.community;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Community.Community;

import okhttp3.Request;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {


    public CommunityFragment() {
        // Required empty public constructor
    }

    GridView gridView;
    CommunityAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        gridView = (GridView)view.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(),CommunityContentActivity.class));
            }
        });
        mAdapter = new CommunityAdapter();
        gridView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),WriteActivity.class));
            }
        });

        NetworkManager.getInstance().getCommunityPost(getContext(), new NetworkManager.OnResultListener<Community>() {
            @Override
            public void onSuccess(Request request, Community result) {
                mAdapter.addAll(result.result.communityData.postList);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
        return view;
    }

}
