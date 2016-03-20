package com.example.sony.banteriorprototype.main.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sony.banteriorprototype.R;

import java.io.File;

public class WriteActivity extends AppCompatActivity {

    public static final String EXTRA_COMMUNITY_CONTENT_MESSAGE = "community_content";
    public static final String EXTRA_FILE = "file";

    int postId = -1;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bundle = new Bundle();

        Intent intent = getIntent();
        postId = intent.getIntExtra(EXTRA_COMMUNITY_CONTENT_MESSAGE, -1);
        String file = intent.getStringExtra(EXTRA_FILE);

        if (postId != -1) {
            bundle.putInt(WriteFragment.EXTRA_POST_ID_MESSAGE, postId);
            bundle.putString(WriteFragment.EXTRA_FILE_URI,file);
            bundle.putInt(HashTagFragment.EXTRA_POST_ID_MESSAGE, postId);
        }
        if (savedInstanceState == null) {
            Fragment wf = new WriteFragment();
            wf.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.write_container, wf)
                    .commit();
        }
    }

    public void changeHashTag() {
        Fragment hf = new HashTagFragment();
        hf.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.write_container, hf)
                .addToBackStack(null)
                .commit();
    }

    File file;
    String content;

    public void setContent(File file, String content) {
        this.file = file;
        this.content = content;
    }

    public File getFile() {
        return file;
    }

    public String getContent() {
        return content;
    }

}
