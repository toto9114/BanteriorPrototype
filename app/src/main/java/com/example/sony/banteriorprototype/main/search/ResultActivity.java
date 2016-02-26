package com.example.sony.banteriorprototype.main.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sony.banteriorprototype.R;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";

    TextView keywordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        String keyword = intent.getStringExtra(EXTRA_MESSAGE);

        keywordView = (TextView)findViewById(R.id.text_keyword);
        keywordView.setText(keyword);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"Result",Toast.LENGTH_SHORT).show();
    }
}
