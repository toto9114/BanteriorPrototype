package com.example.sony.banteriorprototype.main.community;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.CommentData;
import com.example.sony.banteriorprototype.rent.RentalActivity;

public class CommunityContentActivity extends AppCompatActivity{

    RecyclerView recycler;
    ImageView interiorView;
    TextView scrapCountView;
    EditText commentView;
    CommentAdapter mAdapter;
    CommunityPopupWindow popup;
    CommunityToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommunityContentActivity.this, WriteActivity.class));
            }
        });

        recycler = (RecyclerView)findViewById(R.id.recycler_comment);
        mAdapter = new CommentAdapter();
        recycler.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL,false);
        recycler.setLayoutManager(layoutManager);

        toolbar = (CommunityToolbar)findViewById(R.id.community_toolbar);
        toolbar.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_edit:
                        startActivity(new Intent(CommunityContentActivity.this, WriteActivity.class));
                        break;
                    case R.id.btn_delete:
                        startActivity(new Intent(CommunityContentActivity.this,WriteActivity.class));
                        break;
                }
            }
        });

//        popup = new CommunityPopupWindow(this);
//        popup.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view) {
//                    startActivity(new Intent(CommunityContentActivity.this, WriteActivity.class));
//            }
//        });
        interiorView = (ImageView)findViewById(R.id.image_interior);
        scrapCountView = (TextView)findViewById(R.id.text_scrap_count);
        commentView = (EditText)findViewById(R.id.edit_comment);



        Button btn = (Button)findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentData data = new CommentData();
                String comment = commentView.getText().toString();
                data.name = "Unknown";
                data.comment = comment;
                if(!TextUtils.isEmpty(comment)) {
                    mAdapter.add(data);
                }
            }
        });

        Toast.makeText(this,"Community Detail",Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }
        return false;
    }

}
