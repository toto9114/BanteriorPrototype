package com.example.sony.banteriorprototype.main.community;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ListPopupWindow;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.TokenView;
import com.example.sony.banteriorprototype.data.Community.CommunityResult;
import com.example.sony.banteriorprototype.data.PostTypeResult;
import com.example.sony.banteriorprototype.data.Search.HashTagResult;
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
    ListPopupWindow popupWindow;
    ArrayAdapter<String> mAdapter;
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

    OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
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
        popupWindow = new ListPopupWindow(getContext());
        mAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1);
        popupWindow.setAdapter(mAdapter);
        popupWindow.setAnchorView(keywordView);

        keywordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                if (!TextUtils.isEmpty(keyword)) {
                    try {
                        NetworkManager.getInstance().getHashTagResultList(getActivity(), keyword, new NetworkManager.OnResultListener<HashTagResult>() {
                            @Override
                            public void onSuccess(Request request, HashTagResult result) {
                                mAdapter.clear();
                                mAdapter.addAll(result.words);
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    popupWindow.show();
                } else {
                    popupWindow.dismiss();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TokenView hashTag = new TokenView(getContext());
                String keyword = mAdapter.getItem(position);
                hashTag.setToken(keyword);
                hashTag.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view) {

                    }

                    @Override
                    public void onItemClick(View view, int position) {

                    }
                });
                hashTagList.add(keyword);
                mFlowlayout.addView(hashTag, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                keywordView.setText("");
            }
        });

        if(postId != -1){
            NetworkManager.getInstance().getCommunityPost(getContext(), postId, new NetworkManager.OnResultListener<CommunityResult>() {
                @Override
                public void onSuccess(Request request, CommunityResult result) {
                    for(int i=0; i<result.communityDetails.hash_tag.size();i++){
                        TokenView hashTag = new TokenView(getContext());
                        String keyword = result.communityDetails.hash_tag.get(i);
                        hashTagList.add(keyword);
                        hashTag.setToken(keyword);
                        mFlowlayout.addView(hashTag, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        }


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
            if(postId == -1) {
                try {
                    NetworkManager.getInstance().uploadPost(getContext(), file, hashTagList, content, new NetworkManager.OnResultListener<PostTypeResult>() {
                        @Override
                        public void onSuccess(Request request, PostTypeResult result) {
                            Toast.makeText(getContext(), result.result.message, Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }else {
                try {
                    NetworkManager.getInstance().modifyPost(getContext(), postId, file, hashTagList, content, new NetworkManager.OnResultListener<PostTypeResult>() {
                        @Override
                        public void onSuccess(Request request, PostTypeResult result) {
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        if(id == android.R.id.home){
            getActivity().getSupportFragmentManager()
                    .popBackStack();
        }
        return false;
    }
}
