package com.example.sony.banteriorprototype.main.community;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.CommunityData;
import com.example.sony.banteriorprototype.data.InteriorData;


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
        initData();
        return view;
    }
    private void initData(){
        for(int i = 0; i< 10 ; i++) {
            CommunityData data = new CommunityData();
            data.mainImage = InteriorData.MAIN_INTERIOR_IMAGE[i%InteriorData.MAIN_INTERIOR_IMAGE.length];
            data.profileImage = R.drawable.profile_image;
            mAdapter.add(data);
        }

    }

}
