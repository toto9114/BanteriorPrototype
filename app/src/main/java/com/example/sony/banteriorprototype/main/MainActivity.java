package com.example.sony.banteriorprototype.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.main.MainInterior.InteriorFragment;
import com.example.sony.banteriorprototype.main.community.CommunityFragment;
import com.example.sony.banteriorprototype.main.mypage.MyPageFragment;
import com.example.sony.banteriorprototype.main.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    boolean isBackPressed = false;
    public static final int MESSAGE_BACK_KEY_TIMEOUT =0;
    public static final int BACK_KEY_TIME = 2000;

    Handler mHandler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_BACK_KEY_TIMEOUT:
                    isBackPressed = false;
                    return true;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout)findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setCustomView(new TabBangView(MainActivity.this)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(new TabCommunityView(MainActivity.this)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(new TabMypageView(MainActivity.this)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(new TabSearchView(MainActivity.this)));

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.login_container,new InteriorFragment())
                    .commit();
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.login_container, new InteriorFragment())
                                .commit();
                        break;
                    case 1:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.login_container, new CommunityFragment())
                                .commit();
                        break;
                    case 2:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.login_container,new MyPageFragment())
                                .commit();
                        break;
                    case 3:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.login_container,new SearchFragment())
                                .commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(!isBackPressed){
            Toast.makeText(this,"one more back press",Toast.LENGTH_SHORT).show();
            isBackPressed = true;
            mHandler.sendEmptyMessageDelayed(MESSAGE_BACK_KEY_TIMEOUT, BACK_KEY_TIME);
        }
        else {
            mHandler.removeMessages(MESSAGE_BACK_KEY_TIMEOUT);
            finish();
        }
    }
}