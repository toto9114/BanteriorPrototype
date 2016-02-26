package com.example.sony.banteriorprototype.main.mypage;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {


    public MyPageFragment() {
        // Required empty public constructor
    }
    private static final int PICK_IMAGE = 0;
    ImageView profileView;
    TextView nameView,scrapView,myWriteView;
    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        profileView = (ImageView)view.findViewById(R.id.image_profile);
        nameView = (TextView)view.findViewById(R.id.text_name);
        scrapView = (TextView)view.findViewById(R.id.text_scrap);
        myWriteView = (TextView)view.findViewById(R.id.text_write_count);
        gridView = (GridView)view.findViewById(R.id.grid_mypage);
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
            }
        });

        Button btn = (Button)view.findViewById(R.id.btn_myscrap);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getScrapData...
            }
        });

        btn = (Button)view.findViewById(R.id.btn_mywrite);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getMyWritingData...
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode != Activity.RESULT_CANCELED){
            Uri selectedImageUri = data.getData();
            Glide.with(this).load(selectedImageUri).into(profileView);
        }
    }
}
