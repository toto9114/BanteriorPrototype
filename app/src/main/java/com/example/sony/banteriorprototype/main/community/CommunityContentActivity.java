package com.example.sony.banteriorprototype.main.community;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Community.CommunityResult;
import com.example.sony.banteriorprototype.data.PostTypeResult;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class CommunityContentActivity extends AppCompatActivity {

    RecyclerView recycler;
    ImageView interiorView;
    TextView scrapCountView;
    EditText commentView;
    CommentAdapter mAdapter;
    CommunityToolbar communityToolbar;
    Button scrapView;
    public static final String EXTRA_POSTID_MESSAGE = "postid";
    public static final String EXTRA_IS_SCRAP_MESSAGE = "isScrap";
    public static final String EXTRA_FILE = "file";
    int postId;
    String file;
    boolean isScrap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_back);
        getSupportActionBar().setLogo(R.drawable.text_community);
        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommunityContentActivity.this, WriteActivity.class));
            }
        });
        scrapView = (Button)findViewById(R.id.btn_scrap);

        Intent intent = getIntent();
        postId = intent.getIntExtra(EXTRA_POSTID_MESSAGE, 0);
        file = intent.getStringExtra(EXTRA_FILE);

        if(intent.getIntExtra(EXTRA_IS_SCRAP_MESSAGE,0) == 0){
            isScrap = false;
            scrapView.setSelected(false);
        }else {
            isScrap = true;
            scrapView.setSelected(true);
        }

        recycler = (RecyclerView) findViewById(R.id.recycler_comment);
        mAdapter = new CommentAdapter();
        recycler.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);

        communityToolbar = (CommunityToolbar) findViewById(R.id.community_toolbar);
        communityToolbar.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_edit:
                        Intent i = new Intent(new Intent(CommunityContentActivity.this, WriteActivity.class));
                        i.putExtra(WriteActivity.EXTRA_COMMUNITY_CONTENT_MESSAGE,postId);
                        i.putExtra(WriteActivity.EXTRA_FILE,file);
                        startActivity(i);
                        break;
                    case R.id.btn_delete:
                        AlertDialog.Builder builder = new AlertDialog.Builder(CommunityContentActivity.this);
                        builder.setIcon(android.R.drawable.ic_dialog_alert)
                                .setMessage("게시물을 삭제하시겠어요?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(CommunityContentActivity.this,"click",Toast.LENGTH_SHORT).show();
                                        NetworkManager.getInstance().delPost(CommunityContentActivity.this, postId, new NetworkManager.OnResultListener<PostTypeResult>() {
                                            @Override
                                            public void onSuccess(Request request, PostTypeResult result) {
                                                Toast.makeText(CommunityContentActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
                                                finish();
                                            }

                                            @Override
                                            public void onFailure(Request request, int code, Throwable cause) {
                                                Toast.makeText(CommunityContentActivity.this , "error",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                        break;
                }
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });
        interiorView = (ImageView) findViewById(R.id.image_interior);
        scrapCountView = (TextView) findViewById(R.id.text_scrap_count);
        commentView = (EditText) findViewById(R.id.edit_comment);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onItemClick(View view, int position) {
                int replyId = mAdapter.getComment(position).id;
                NetworkManager.getInstance().delReply(CommunityContentActivity.this, postId, replyId, new NetworkManager.OnResultListener<PostTypeResult>() {
                    @Override
                    public void onSuccess(Request request, PostTypeResult result) {
                        Toast.makeText(CommunityContentActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
                        NetworkManager.getInstance().getCommunityPost(CommunityContentActivity.this, postId, new NetworkManager.OnResultListener<CommunityResult>() {
                            @Override
                            public void onSuccess(Request request, CommunityResult result) {
                                mAdapter.clear();
                                Glide.with(CommunityContentActivity.this).load(result.communityDetails.mainImage).into(interiorView);
                                scrapCountView.setText("" + result.communityDetails.scrap_count);
                                communityToolbar.setToolbar(result.communityDetails);
                                mAdapter.addAll(result.communityDetails.reply);
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
            }
        });

        Button btn = (Button) findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String comment = commentView.getText().toString();
                if (!TextUtils.isEmpty(comment)) {
                    try {
                        NetworkManager.getInstance().setComment(CommunityContentActivity.this, postId, comment, new NetworkManager.OnResultListener<PostTypeResult>() {
                            @Override
                            public void onSuccess(Request request, PostTypeResult result) {
                                //Toast.makeText(CommunityContentActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
                                NetworkManager.getInstance().getCommunityPost(CommunityContentActivity.this, postId, new NetworkManager.OnResultListener<CommunityResult>() {
                                    @Override
                                    public void onSuccess(Request request, CommunityResult result) {
                                        mAdapter.clear();
                                        Glide.with(CommunityContentActivity.this).load(result.communityDetails.mainImage).into(interiorView);
                                        scrapCountView.setText("" + result.communityDetails.scrap_count);
                                        communityToolbar.setToolbar(result.communityDetails);
                                        mAdapter.addAll(result.communityDetails.reply);
                                        commentView.setText("");
                                    }

                                    @Override
                                    public void onFailure(Request request, int code, Throwable cause) {

                                    }
                                });
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


        scrapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isScrap){
                    NetworkManager.getInstance().undoScrap(CommunityContentActivity.this, postId, new NetworkManager.OnResultListener<PostTypeResult>() {
                        @Override
                        public void onSuccess(Request request, PostTypeResult result) {
                            Toast.makeText(CommunityContentActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
                            scrapView.setSelected(false);
                            isScrap = false;
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                }
                else {
                    NetworkManager.getInstance().doScrap(CommunityContentActivity.this, postId, new NetworkManager.OnResultListener<PostTypeResult>() {
                        @Override
                        public void onSuccess(Request request, PostTypeResult result) {
                            Toast.makeText(CommunityContentActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
                            scrapView.setSelected(true);
                            isScrap = true;
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                }
            }
        });

        NetworkManager.getInstance().getCommunityPost(this, postId, new NetworkManager.OnResultListener<CommunityResult>() {
            @Override
            public void onSuccess(Request request, CommunityResult result) {
                Glide.with(CommunityContentActivity.this).load(result.communityDetails.mainImage).into(interiorView);
                scrapCountView.setText("" + result.communityDetails.scrap_count);
                communityToolbar.setToolbar(result.communityDetails);
                mAdapter.addAll(result.communityDetails.reply);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

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
