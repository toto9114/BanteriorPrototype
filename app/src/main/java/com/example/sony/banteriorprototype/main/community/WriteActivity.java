package com.example.sony.banteriorprototype.main.community;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sony.banteriorprototype.R;

import java.io.File;

public class WriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.write_container,new WriteFragment())
                    .commit();
        }
    }
    public void changeHashTag(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.write_container,new HashTagFragment())
                .addToBackStack(null)
                .commit();
    }
    File file;
    String content;
    public void setContent(File file,String content){
        this.file = file;
        this.content = content;
    }
    public File getFile(){
        return file;
    }
    public String getContent(){
        return content;
    }

}
