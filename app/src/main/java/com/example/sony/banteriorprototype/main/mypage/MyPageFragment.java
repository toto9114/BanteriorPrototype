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

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Mypage.MyPageScrap;
import com.example.sony.banteriorprototype.data.Mypage.MyPost;
import com.example.sony.banteriorprototype.data.Mypage.MyProfileData;
import com.example.sony.banteriorprototype.data.Mypage.MyProfile;
import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.main.MainInterior.DetailInterior.InteriorActivity;

import okhttp3.Request;


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

//        NetworkManager.getInstance().getMypage(new NetworkManager.OnResultListener<MyProfileData>() {
//            @Override
//            public void onSuccess(MyProfileData result) {
//                setMyPage(result);
//            }
//
//            @Override
//            public void onFailure(int code) {
//
//            }
//        });

        NetworkManager.getInstance().getMypage(new NetworkManager.OnResultListener<MyProfile>() {
            @Override
            public void onSuccess(Request request, MyProfile result) {
                setMyPage(result.result.mypageData);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
        Button btn = (Button)view.findViewById(R.id.btn_myscrap);
        btn.setSelected(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getScrapData...
                mAdapter.clear();
                NetworkManager.getInstance().getMyScrap(new NetworkManager.OnResultListener<MyPageScrap>() {
                    @Override
                    public void onSuccess(Request request, MyPageScrap result) {
                        mAdapter.addAll(result.result.data.list);
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

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
                NetworkManager.getInstance().getMyPost(new NetworkManager.OnResultListener<MyPost>() {
                    @Override
                    public void onSuccess(Request request, MyPost result) {
                        mAdapter.addAll(result.result.data.list);
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

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

    MyProfileData data;
    private void setMyPage(MyProfileData data){
        this.data = data;
        nameView.setText(data.getName());
        Glide.with(getContext())
                .load(data.getProfileImage())
                .into(profileView);
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
