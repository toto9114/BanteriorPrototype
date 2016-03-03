package com.example.sony.banteriorprototype.main.mypage;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.MyPageData;
import com.example.sony.banteriorprototype.data.MyWritingInfo;
import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.data.ScrapData;
import com.example.sony.banteriorprototype.main.MainInterior.DetailInterior.InteriorActivity;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {


    public MyPageFragment() {
        // Required empty public constructor
    }
    private static final int PICK_IMAGE = 0;
    ImageView profileView;
    TextView nameView,scrapView, myPostView;
    GridView gridView;
    MyPageAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        profileView = (ImageView)view.findViewById(R.id.image_profile);
        nameView = (TextView)view.findViewById(R.id.text_name);
        scrapView = (TextView)view.findViewById(R.id.text_scrap);
        myPostView = (TextView)view.findViewById(R.id.text_write_count);

        gridView = (GridView)view.findViewById(R.id.grid_mypage);
        mAdapter = new MyPageAdapter();
        gridView.setAdapter(mAdapter);

        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
            }
        });

        NetworkManager.getInstance().getMypage(new NetworkManager.OnResultListener<MyPageData>() {
            @Override
            public void onSuccess(MyPageData result) {
                setMyPage(result);
            }

            @Override
            public void onFailure(int code) {

            }
        });

        Button btn = (Button)view.findViewById(R.id.btn_myscrap);
        btn.setSelected(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getScrapData...
                mAdapter.clear();
                NetworkManager.getInstance().getScrapData(new NetworkManager.OnResultListener<List<ScrapData>>() {
                    @Override
                    public void onSuccess(List<ScrapData> result) {
                        for (ScrapData s : result) {
                            mAdapter.add(s);
                        }
                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });

            }
        });

        btn = (Button)view.findViewById(R.id.btn_mywrite);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getMyWritingData...
                mAdapter.clear();
                NetworkManager.getInstance().getMyWritingData(new NetworkManager.OnResultListener<List<MyWritingInfo>>() {
                    @Override
                    public void onSuccess(List<MyWritingInfo> result) {
                        for (MyWritingInfo i : result) {
                            mAdapter.add(i);
                        }
                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(), InteriorActivity.class));
            }
        });
        btn = (Button)view.findViewById(R.id.btn_notify);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),NotificationActivity.class));
            }
        });
        return view;
    }

    MyPageData data;
    private void setMyPage(MyPageData data){
        this.data = data;
        nameView.setText(data.getName());
        scrapView.setText(data.scrapCount);
        myPostView.setText(data.myPostCount);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode != Activity.RESULT_CANCELED){
            Uri selectedImageUri = data.getData();
            Glide.with(this).load(selectedImageUri).into(profileView);
        }
    }
}
