package com.example.sony.banteriorprototype.main.mypage;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import com.example.sony.banteriorprototype.GCM.PropertyManager;
import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.PostTypeResult;

import okhttp3.Request;

public class NotificationActivity extends AppCompatActivity {

    private static final int[] NOTIFY_MESSAGE = {R.string.message_due, R.string.message_reply};
    ListView listView;
    NotifyAdapter mAdapter;
    ImageView titleView;
    Switch pushSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View titleBar = getLayoutInflater().inflate(R.layout.view_center_toolbar, null);
        titleView = (ImageView) titleBar.findViewById(R.id.image_title);
        titleView.setImageResource(R.drawable.text_alarm);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(titleBar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        getSupportActionBar().setHomeAsUpIndicator(null);

        pushSwitch = (Switch) findViewById(R.id.switch_reply_push);
        listView = (ListView) findViewById(R.id.notify_listView);
        mAdapter = new NotifyAdapter();
        listView.setAdapter(mAdapter);

        boolean isPush = PropertyManager.getInstance().isPush();
        pushSwitch.setChecked(isPush);
        pushSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                pushSwitch.setChecked(isChecked);
                int push;
                if(isChecked) push =1;
                else push =0;
                NetworkManager.getInstance().push(NotificationActivity.this, push, new NetworkManager.OnResultListener<PostTypeResult>() {
                    @Override
                    public void onSuccess(Request request, PostTypeResult result) {
                        PropertyManager.getInstance().setPush(isChecked);
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
            }
        });
        initData();
    }

    private void initData() {
//        Random r = new Random();
//        for (int i = 0; i < 5; i++) {
//            NotificationData data = new NotificationData();
//            data.notifyIcon = R.mipmap.ic_launcher;
//            data.notification = getString(NOTIFY_MESSAGE[(r.nextInt(2) + i) % NOTIFY_MESSAGE.length]);
//            mAdapter.add(data);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_finish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.finish) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
