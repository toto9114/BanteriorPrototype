package com.example.sony.banteriorprototype.main.community;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
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

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.TokenView;
import com.example.sony.banteriorprototype.data.Community.CommunityResult;
import com.example.sony.banteriorprototype.data.PostTypeResult;
import com.wefika.flowlayout.FlowLayout;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class HashTagFragment extends Fragment {


    public HashTagFragment() {
        // Required empty public constructor
    }

    EditText keywordView;
    FlowLayout mFlowlayout;
    List<String> hashTagList = new ArrayList<>();
    File file;
    String content;
    ImageView titleView;

    public static final String EXTRA_POST_ID_MESSAGE = "postId";
    int postId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!= null){
            Bundle args = getArguments();
            postId = args.getInt(EXTRA_POST_ID_MESSAGE,-1);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_hash_tag, container, false);
        View toolbar = getLayoutInflater(savedInstanceState).inflate(R.layout.view_center_toolbar, null);
        titleView = (ImageView)toolbar.findViewById(R.id.image_title);
        titleView.setImageResource(R.drawable.text_tag);

        ((WriteActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((WriteActivity)getActivity()).getSupportActionBar().setCustomView(toolbar,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        ((WriteActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((WriteActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_exit);
        setHasOptionsMenu(true);
        keywordView = (EditText)view.findViewById(R.id.edit_hash);
        mFlowlayout = (FlowLayout)view.findViewById(R.id.flowlayout);

        if(postId != -1){
            NetworkManager.getInstance().getCommunityPost(getContext(), postId, new NetworkManager.OnResultListener<CommunityResult>() {
                @Override
                public void onSuccess(Request request, CommunityResult result) {
                    for(int i=0; i<result.communityDetails.hash_tag.size();i++){
                        TokenView hashTag = new TokenView(getContext());
                        hashTag.setToken(result.communityDetails.hash_tag.get(i));
                        mFlowlayout.addView(hashTag, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        }

        Button btn = (Button)view.findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokenView hashTag = new TokenView(getContext());
                String keyword = keywordView.getText().toString();
                hashTag.setToken(keyword);
                hashTagList.add(keyword);
                mFlowlayout.addView(hashTag,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        file = ((WriteActivity) getActivity()).getFile();
        content = ((WriteActivity) getActivity()).getContent();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_regist_post,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.regist_post){
            getActivity().finish();
            try {
                NetworkManager.getInstance().uploadPost(getContext(), file, hashTagList, content, new NetworkManager.OnResultListener<PostTypeResult>() {
                    @Override
                    public void onSuccess(Request request, PostTypeResult result) {
                        Toast.makeText(getContext(), result.result.message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return true;
        }
        if(id == android.R.id.home){
            getActivity().getSupportFragmentManager()
                    .popBackStack();
        }
        return false;
    }
}
