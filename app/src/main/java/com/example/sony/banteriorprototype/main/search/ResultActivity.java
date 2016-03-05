package com.example.sony.banteriorprototype.main.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Search.Search;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";

    TextView keywordView;
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
        keywordView = (TextView) findViewById(R.id.text_keyword);
        keywordView.setText(keyword);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
