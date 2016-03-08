package com.example.sony.banteriorprototype.main.community;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.CommentData;
import com.example.sony.banteriorprototype.data.PostTypeResult;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class CommunityContentActivity extends AppCompatActivity {

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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommunityContentActivity.this, WriteActivity.class));
            }
        });

        recycler = (RecyclerView) findViewById(R.id.recycler_comment);
        mAdapter = new CommentAdapter();
        recycler.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);

        toolbar = (CommunityToolbar) findViewById(R.id.community_toolbar);
        toolbar.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_edit:
                        startActivity(new Intent(CommunityContentActivity.this, WriteActivity.class));
                        break;
                    case R.id.btn_delete:
                        AlertDialog.Builder builder =new AlertDialog.Builder(CommunityContentActivity.this);
                        builder.setIcon(android.R.drawable.ic_dialog_alert)
                                .setMessage("게시물을 삭제하시겠어요?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        NetworkManager.getInstance().delPost(CommunityContentActivity.this, 1, new NetworkManager.OnResultListener<PostTypeResult>() {
                                            @Override
                                            public void onSuccess(Request request, PostTypeResult result) {

                                            }

                                            @Override
                                            public void onFailure(Request request, int code, Throwable cause) {

                                            }
                                        });
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        break;
                }
            }
        });
        interiorView = (ImageView) findViewById(R.id.image_interior);
        scrapCountView = (TextView) findViewById(R.id.text_scrap_count);
        commentView = (EditText) findViewById(R.id.edit_comment);


        Button btn = (Button) findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CommentData data = new CommentData();
//                String comment = commentView.getText().toString();
//                data.username = "Unknown";
//                data.reply_content = comment;
//                if(!TextUtils.isEmpty(comment)) {
//                    mAdapter.add(data);
//                }
                String comment = commentView.getText().toString();
                if(!TextUtils.isEmpty(comment)) {
                    try {
                        NetworkManager.getInstance().setComment(CommunityContentActivity.this, 1, comment, new NetworkManager.OnResultListener<CommentData>() {
                            @Override
                            public void onSuccess(Request request, CommentData result) {
                                Toast.makeText(CommunityContentActivity.this, "OK", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

}
