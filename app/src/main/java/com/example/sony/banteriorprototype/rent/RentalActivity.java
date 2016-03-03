package com.example.sony.banteriorprototype.rent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.ProductData;

import java.util.List;

public class RentalActivity extends AppCompatActivity {

    RecyclerView orderView;
    OrderAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);

        orderView = (RecyclerView)findViewById(R.id.orderView);
        mAdapter = new OrderAdapter();
        orderView.setAdapter(mAdapter);
        NetworkManager.getInstance().getProductData(new NetworkManager.OnResultListener<List<ProductData>>() {
            @Override
            public void onSuccess(List<ProductData> result) {
                for(ProductData data : result){
                    mAdapter.add(data);
                }
            }

            @Override
            public void onFailure(int code) {

            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        orderView.setLayoutManager(layoutManager);
        Button btn = (Button) findViewById(R.id.btn_pay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RentalActivity.this, getString(R.string.complete_payment), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
