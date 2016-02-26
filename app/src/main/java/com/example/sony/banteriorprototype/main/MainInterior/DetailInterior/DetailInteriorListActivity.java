package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.InteriorData;

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
        initData();
    }
    private void initData(){
        for(int i = 0 ;i < 16 ; i++){
            InteriorData data= new InteriorData();
            data.interiorImage = MAIN_INTERIOR_IMAGE[i%MAIN_INTERIOR_IMAGE.length];
            mAdapter.add(data);
        }
    }
}
