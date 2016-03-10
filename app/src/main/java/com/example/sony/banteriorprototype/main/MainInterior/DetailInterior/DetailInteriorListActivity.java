package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;
import com.example.sony.banteriorprototype.data.Interior.InteriorResult;

import okhttp3.Request;

public class DetailInteriorListActivity extends AppCompatActivity {
    GridView gridView;
    DetailInteriorAdapter mAdapter;

    public static final String EXTRA_CATEGORY_MESSAGE = "category";
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_interior_list);

        Intent intent = getIntent();
        category = intent.getStringExtra(EXTRA_CATEGORY_MESSAGE);

        gridView = (GridView)findViewById(R.id.grid_interior_list);
        mAdapter = new DetailInteriorAdapter();
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DetailInteriorListActivity.this, InteriorActivity.class);
                intent.putExtra(InteriorActivity.EXTRA_INTERIOR_MESSAGE,((InteriorContentData)gridView.getItemAtPosition(position)).post_id);
                intent.putExtra(InteriorActivity.EXTRA_CATEGORY_MESSAGE,((InteriorContentData)gridView.getItemAtPosition(position)).category);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        NetworkManager.getInstance().getInteriorPostList(this, category , new NetworkManager.OnResultListener<InteriorResult>() {
            @Override
            public void onSuccess(Request request, InteriorResult result) {
                for(InteriorContentData data : result.postData.interiorList) {
                    mAdapter.add(data);
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }
}
