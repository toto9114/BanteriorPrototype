package com.example.sony.banteriorprototype.main.community;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Community.CommunityContentData;
import com.example.sony.banteriorprototype.data.Community.CommunityResult;

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
                Intent intent = new Intent(getContext(),CommunityContentActivity.class);
                intent.putExtra(CommunityContentActivity.EXTRA_POSTID_MESSAGE, ((CommunityContentData)gridView.getItemAtPosition(position)).post_id);
                intent.putExtra(CommunityContentActivity.EXTRA_IS_SCRAP_MESSAGE, ((CommunityContentData)gridView.getItemAtPosition(position)).state);
                intent.putExtra(CommunityContentActivity.EXTRA_FILE,((CommunityContentData) gridView.getItemAtPosition(position)).mainImage);
                startActivity(intent);
            }
        });
        mAdapter = new CommunityAdapter();
        gridView.setAdapter(mAdapter);

        Button fab = (Button)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),WriteActivity.class);
                startActivity(intent);
            }
        });

        NetworkManager.getInstance().getCommunityPostList(getContext(), new NetworkManager.OnResultListener<CommunityResult>() {
            @Override
            public void onSuccess(Request request, CommunityResult result) {
                mAdapter.addAll(result.communityData.communityList);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
        NetworkManager.getInstance().getCommunityPostList(getContext(), new NetworkManager.OnResultListener<CommunityResult>() {
            @Override
            public void onSuccess(Request request, CommunityResult result) {
                mAdapter.addAll(result.communityData.communityList);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }
}
