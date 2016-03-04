package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.main.community.CommunityContentActivity;

public class DetailInteriorListActivity extends AppCompatActivity {
    private static final int[] MAIN_INTERIOR_IMAGE = {R.drawable.modern_main1,
            R.drawable.modern_main2,
            R.drawable.modern_main3,
            R.drawable.modern_main4};

    GridView gridView;
    DetailInteriorAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_interior_list);
        gridView = (GridView)findViewById(R.id.grid_interior_list);
        mAdapter = new DetailInteriorAdapter();
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(DetailInteriorListActivity.this, CommunityContentActivity.class));
            }
        });
//        NetworkManager.getInstance().getDetailInteriorData(new NetworkManager.OnResultListener<List<InteriorContentData>>() {
//            @Override
//            public void onSuccess(List<InteriorContentData> result) {
//                for(InteriorContentData data : result) {
//                    mAdapter.add(data);
//                }
//            }
//
//            @Override
//            public void onFailure(int code) {
//
//            }
//        });
    }
}
