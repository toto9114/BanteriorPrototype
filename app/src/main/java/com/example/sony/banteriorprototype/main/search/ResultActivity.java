package com.example.sony.banteriorprototype.main.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Search.Search;
import com.example.sony.banteriorprototype.data.Search.SearchContentData;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";

    GridView resultView;
    SearchResultAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        String keyword = intent.getStringExtra(EXTRA_MESSAGE);
        resultView = (GridView) findViewById(R.id.grid_search);
        mAdapter = new SearchResultAdapter();
        resultView.setAdapter(mAdapter);

        try {
            NetworkManager.getInstance().getHashTagResult(this, keyword, new NetworkManager.OnResultListener<Search>() {
                @Override
                public void onSuccess(Request request, Search result) {
                    mAdapter.clear();
                    mAdapter.addAll(result.result.postData.postList);
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        resultView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchContentData data= (SearchContentData)resultView.getItemAtPosition(position);
//                if(data.category != null){
//                    Intent i = new Intent(ResultActivity.this, CommunityContentActivity.class);
//                    i.putExtra(CommunityContentActivity.EXTRA_POSTID_MESSAGE,data.post_id);
//                    startActivity(i);
//                }else {
//                    Intent i = new Intent(ResultActivity.this, InteriorActivity.class);
//                    i.putExtra(InteriorActivity.EXTRA_INTERIOR_MESSAGE,data.post_id);
//                    i.putExtra(InteriorActivity.EXTRA_CATEGORY_MESSAGE,data.category);
//                    startActivity(i);
//                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
