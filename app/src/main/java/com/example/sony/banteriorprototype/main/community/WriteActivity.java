package com.example.sony.banteriorprototype.main.community;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sony.banteriorprototype.R;

public class WriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
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
}
