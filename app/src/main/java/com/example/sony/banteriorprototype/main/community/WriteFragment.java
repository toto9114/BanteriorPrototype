package com.example.sony.banteriorprototype.main.community;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.PostTypeResult;

import java.io.File;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteFragment extends Fragment {


    public WriteFragment() {
        // Required empty public constructor
    }

    private static final int PICK_IMAGE = 0;
    ImageView imageView;
    EditText contentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        ActionBar actionBar = ((WriteActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);

        imageView = (ImageView)view.findViewById(R.id.image_load);
        contentView = (EditText)view.findViewById(R.id.edit_content);

        Button btn = (Button)view.findViewById(R.id.btn_camera);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "camera", Toast.LENGTH_SHORT).show();
            }
        });
        btn = (Button)view.findViewById(R.id.btn_gallery);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "gallery", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode != Activity.RESULT_CANCELED){
            Uri selectedImageUri = data.getData();
            Cursor c = getContext().getContentResolver().query(selectedImageUri, new String[] {MediaStore.Images.Media.DATA}, null, null, null);
            if (!c.moveToNext()) {
                c.close();
                return;
            }
            String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
            c.close();
            File file = new File(path);
            NetworkManager.getInstance().setMyProfile(getActivity(), file, new NetworkManager.OnResultListener<PostTypeResult>() {
                @Override
                public void onSuccess(Request request, PostTypeResult result) {

                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
            Glide.with(this).load(selectedImageUri).into(imageView);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_regist_hash,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.regist_hash) {
            ((WriteActivity)getActivity()).changeHashTag();
            return true;
        }
        return false;
    }
}
