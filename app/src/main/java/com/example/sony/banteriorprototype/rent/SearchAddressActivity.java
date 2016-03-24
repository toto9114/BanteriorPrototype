package com.example.sony.banteriorprototype.rent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.POIItem;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPOIItem;

import java.util.ArrayList;

public class SearchAddressActivity extends AppCompatActivity {

    private static final String API_KEY = "4fa24891-48c0-3616-8cd5-a86e8d323d41";
    EditText keywordView;
    ListView listView;
    ArrayAdapter<POIItem> mAdapter;
    public static final int RC_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);

        listView = (ListView)findViewById(R.id.listView);
        keywordView = (EditText)findViewById(R.id.edit_search);
        mAdapter = new ArrayAdapter<POIItem>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                POIItem item = (POIItem) listView.getItemAtPosition(position);
                String address = item.toString();
                Intent intent = new Intent();
                intent.putExtra("address",address);
                setResult(RC_CODE,intent);
                finish();
            }
        });
        Button btn = (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = keywordView.getText().toString();
                if (!TextUtils.isEmpty(keyword)) {
                    searchPOI(keyword);
                }
            }
        });
    }
    private void searchPOI(String keyword){
        TMapData data = new TMapData();
        data.findAllPOI(keyword, new TMapData.FindAllPOIListenerCallback() {
            @Override
            public void onFindAllPOI(final ArrayList<TMapPOIItem> arrayList) {
                mAdapter.clear();
                for (TMapPOIItem poi : arrayList) {
                    mAdapter.add(new POIItem(poi));
                }
            }
        });
    }
}
