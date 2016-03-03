package com.example.sony.banteriorprototype.main.mypage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.NotificationData;

import java.util.Random;

public class NotificationActivity extends AppCompatActivity {

    private static final int[] NOTIFY_MESSAGE = {R.string.message_due,R.string.message_reply};
    ListView listView;
    NotifyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        listView = (ListView)findViewById(R.id.notify_listView);
        mAdapter = new NotifyAdapter();
        listView.setAdapter(mAdapter);
        initData();
    }
    private void initData(){
        Random r = new Random();
        for(int i = 0 ; i< 5 ; i++) {
            NotificationData data = new NotificationData();
            data.notifyIcon = R.mipmap.ic_launcher;
            data.notification = getString(NOTIFY_MESSAGE[(r.nextInt(2)+i)%NOTIFY_MESSAGE.length]);
            mAdapter.add(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_finish,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.finish){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
