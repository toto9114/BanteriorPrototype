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
import com.example.sony.banteriorprototype.data.AddressInfo;
import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;
import com.example.sony.banteriorprototype.data.Interior.InteriorResult;
import com.example.sony.banteriorprototype.data.PostTypeResult;
import com.example.sony.banteriorprototype.main.community.OnItemClickListener;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class RentalActivity extends AppCompatActivity{

    public static final String EXTRA_PRODUCT_MESSAGE ="product";
    public static final String EXTRA_POST_ID_MESSAGE ="postId";
    public static final String EXTRA_CATEGORY_MESSAGE ="category";
    RecyclerView orderView;
    OrderAdapter mAdapter;
    InteriorContentData interiorContentData;

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
        interiorContentData = (InteriorContentData)intent.getSerializableExtra(EXTRA_PRODUCT_MESSAGE);

        orderView = (RecyclerView)findViewById(R.id.orderView);
        mAdapter = new OrderAdapter();


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        orderView.setLayoutManager(layoutManager);

        try {
            NetworkManager.getInstance().getInteriorPost(this, interiorContentData.post_id, interiorContentData.category, new NetworkManager.OnResultListener<InteriorResult>() {
                @Override
                public void onSuccess(Request request, InteriorResult result) {
                    mAdapter.setInterior(result.detailData);
                    mAdapter.addAll(result.detailData.productDataList);
                    orderView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Button btn = (Button) findViewById(R.id.btn_pay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressInfo info = mAdapter.info;
                info.total_price = interiorContentData.month_price * info.period;
                NetworkManager.getInstance().setOrder(RentalActivity.this, interiorContentData.post_id, info.address, info.phone, info.total_price, info.paymethod, info.period, new NetworkManager.OnResultListener<PostTypeResult>() {
                    @Override
                    public void onSuccess(Request request, PostTypeResult result) {
                        Toast.makeText(RentalActivity.this,result.result.message,Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
                Toast.makeText(RentalActivity.this, getString(R.string.complete_payment), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                MyDatePickerDialog f = new MyDatePickerDialog();
                f.show(getSupportFragmentManager(), "dialog");
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });

    }
    public void setDate(int year, int month){
        mAdapter.setDate(year,month);
    }

}
