package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.InteriorData;
import com.example.sony.banteriorprototype.main.MainActivity;

public class RentalActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);
        Button btn = (Button)findViewById(R.id.btn_pay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
