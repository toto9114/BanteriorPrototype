package com.example.sony.banteriorprototype.main.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Search.Search;
import com.example.sony.banteriorprototype.data.Search.SearchContentData;
import com.example.sony.banteriorprototype.main.MainInterior.DetailInterior.InteriorActivity;
import com.example.sony.banteriorprototype.main.community.CommunityContentActivity;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";
    private static final String COMMUNITY_DATA = "community";
    GridView resultView;
    SearchResultAdapter mAdapter;
    boolean isLast = false;
    String keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.text_search);

        Intent intent = getIntent();
        keyword = intent.getStringExtra(EXTRA_MESSAGE);
        resultView = (GridView) findViewById(R.id.grid_search);
        mAdapter = new SearchResultAdapter();
        resultView.setAdapter(mAdapter);

        try {
            NetworkManager.getInstance().getHashTagResult(this, keyword,1, new NetworkManager.OnResultListener<Search>() {
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
                if(data.category.equals(COMMUNITY_DATA)){
                    Intent i = new Intent(ResultActivity.this, CommunityContentActivity.class);
                    i.putExtra(CommunityContentActivity.EXTRA_POSTID_MESSAGE,data.post_id);
                    startActivity(i);
                }else {
                    Intent i = new Intent(ResultActivity.this, InteriorActivity.class);
                    i.putExtra(InteriorActivity.EXTRA_INTERIOR_MESSAGE,data.post_id);
                    i.putExtra(InteriorActivity.EXTRA_CATEGORY_MESSAGE,data.category);
                    startActivity(i);
                }
            }
        });

        resultView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLast && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    getMoreItem();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
                    isLast = true;
                } else {
                    isLast = false;
                }
            }
        });
    }

    boolean isMoreData = false;
    private void getMoreItem(){
        if(isMoreData) return;
        isMoreData = true;

        int page = mAdapter.getCurrentPage()+1;
        mAdapter.setCurrentPage(page);
        try {
            NetworkManager.getInstance().getHashTagResult(this, keyword, 1, new NetworkManager.OnResultListener<Search>() {
                @Override
                public void onSuccess(Request request, Search result) {
                    mAdapter.addAll(result.result.postData.postList);
                    isMoreData = false;
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {
                    isMoreData = false;
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
