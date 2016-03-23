package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Category;
import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;
import com.example.sony.banteriorprototype.data.Interior.InteriorResult;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class DetailInteriorListActivity extends AppCompatActivity {
    GridView gridView;
    DetailInteriorAdapter mAdapter;

    public static final String EXTRA_CATEGORY_MESSAGE = "category";
    String category;
    boolean isLast = false;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_interior_list);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_back);

        Intent intent = getIntent();
        category = intent.getStringExtra(EXTRA_CATEGORY_MESSAGE);

        for(int i=0 ; i< Category.CATEGORY_ARRAY.length; i++){
            if(category.equals(Category.CATEGORY_ARRAY[i])) {
                toolbar.setLogo(Category.TITLE_IMAGE_ARRAY[i]);
                break;
            }
        }

        gridView = (GridView)findViewById(R.id.grid_interior_list);
        mAdapter = new DetailInteriorAdapter();
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DetailInteriorListActivity.this, InteriorActivity.class);
                //intent.putExtra(InteriorActivity.EXTRA_INTERIOR_MESSAGE,((InteriorContentData)gridView.getItemAtPosition(position)).post_id);
                intent.putExtra(InteriorActivity.EXTRA_INTERIOR_MESSAGE,position);
                intent.putExtra(InteriorActivity.EXTRA_CATEGORY_MESSAGE,((InteriorContentData)gridView.getItemAtPosition(position)).category);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        try {
            NetworkManager.getInstance().getInteriorPostList(this, category ,1,new NetworkManager.OnResultListener<InteriorResult>() {
                @Override
                public void onSuccess(Request request, InteriorResult result) {
                    mAdapter.clear();
                    for(InteriorContentData data : result.postData.interiorList) {
                        mAdapter.add(data);
                    }
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
            NetworkManager.getInstance().getInteriorPostList(this, category, page,new NetworkManager.OnResultListener<InteriorResult>() {
                @Override
                public void onSuccess(Request request, InteriorResult result) {
                    mAdapter.addAll(result.postData.interiorList);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
