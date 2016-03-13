package com.example.sony.banteriorprototype.rent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;
import com.example.sony.banteriorprototype.data.Interior.InteriorResult;
import com.example.sony.banteriorprototype.data.PostTypeResult;

import okhttp3.Request;

public class RentalActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_MESSAGE ="product";
    RecyclerView orderView;
    OrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_back);
        getSupportActionBar().setLogo(R.drawable.text_buy);
        Intent intent = getIntent();
        final InteriorContentData interiorContentData = (InteriorContentData)intent.getSerializableExtra(EXTRA_PRODUCT_MESSAGE);

        orderView = (RecyclerView)findViewById(R.id.orderView);
        mAdapter = new OrderAdapter();
        orderView.setAdapter(mAdapter);
        mAdapter.setInterior(interiorContentData);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        orderView.setLayoutManager(layoutManager);

        NetworkManager.getInstance().getInteriorPost(this, interiorContentData.post_id, interiorContentData.category, new NetworkManager.OnResultListener<InteriorResult>() {
            @Override
            public void onSuccess(Request request, InteriorResult result) {
                mAdapter.addAll(result.detailData.productDataList);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });

        Button btn = (Button) findViewById(R.id.btn_pay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkManager.getInstance().setOrder(RentalActivity.this, interiorContentData.post_id, "", "", 1, "", 1, new NetworkManager.OnResultListener<PostTypeResult>() {
                    @Override
                    public void onSuccess(Request request, PostTypeResult result) {
                        Toast.makeText(RentalActivity.this,result.result.message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
                Toast.makeText(RentalActivity.this, getString(R.string.complete_payment), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
