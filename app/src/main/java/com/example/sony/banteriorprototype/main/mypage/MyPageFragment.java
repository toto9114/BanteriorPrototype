package com.example.sony.banteriorprototype.main.mypage;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Mypage.MyPageScrap;
import com.example.sony.banteriorprototype.data.Mypage.MyPost;
import com.example.sony.banteriorprototype.data.Mypage.MyPostData;
import com.example.sony.banteriorprototype.data.Mypage.MyProfileData;
import com.example.sony.banteriorprototype.data.Mypage.ScrapData;
import com.example.sony.banteriorprototype.data.PostTypeResult;
import com.example.sony.banteriorprototype.main.MainInterior.DetailInterior.InteriorActivity;
import com.example.sony.banteriorprototype.main.community.CommunityContentActivity;

import java.io.File;

import okhttp3.Request;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {


    public MyPageFragment() {
        // Required empty public constructor
    }
    private static final String COMMUNITY_DATA = "community";
    private static final int PICK_IMAGE = 0;
    ImageView profileView;
    TextView nameView, scrapView, myPostView;
    GridView gridView;
    MyPageScrapAdapter scrapAdapter;
    MyPostAdapter postAdapter;
    TabLayout tabLayout;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        profileView = (ImageView) view.findViewById(R.id.image_profile);
        nameView = (TextView) view.findViewById(R.id.text_brand);
        scrapView = (TextView) view.findViewById(R.id.text_scrap);
        myPostView = (TextView) view.findViewById(R.id.text_write_count);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);

        gridView = (GridView) view.findViewById(R.id.grid_mypage);
        scrapAdapter = new MyPageScrapAdapter();
        postAdapter = new MyPostAdapter();
        gridView.setAdapter(scrapAdapter);

        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
            }
        });


//        NetworkManager.getInstance().getMypage(getContext(), new NetworkManager.OnResultListener<MyProfileData>() {
//            @Override
//            public void onSuccess(Request request, MyProfileData result) {
//                setMyPage(result);
//            }
//
//            @Override
//            public void onFailure(Request request, int code, Throwable cause) {
//
//            }
//        });

        NetworkManager.getInstance().getMyScrap(getActivity(), new NetworkManager.OnResultListener<MyPageScrap>() {
            @Override
            public void onSuccess(Request request, MyPageScrap result) {
                scrapAdapter.clear();
                scrapAdapter.addAll(result.result.scrapData.postList);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("스크랩"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("내가 쓴 글"), 1);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(),R.color.tab_select_text));
        tabLayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.tab_unselect_text),
                ContextCompat.getColor(getContext(), R.color.tab_select_text));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        gridView.setAdapter(scrapAdapter);
                        scrapAdapter.clear();
                        NetworkManager.getInstance().getMyScrap(getContext(), new NetworkManager.OnResultListener<MyPageScrap>() {
                            @Override
                            public void onSuccess(Request request, MyPageScrap result) {
                                scrapAdapter.addAll(result.result.scrapData.postList);
                                Toast.makeText(getActivity(), result.result.message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                        break;
                    case 1:
                        gridView.setAdapter(postAdapter);
                        postAdapter.clear();
                        NetworkManager.getInstance().getMyPost(getContext(), new NetworkManager.OnResultListener<MyPost>() {
                            @Override
                            public void onSuccess(Request request, MyPost result) {
                                postAdapter.addAll(result.result.postData.postList);
                                Toast.makeText(getActivity(), result.result.message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        ScrapData interiorData = (ScrapData) gridView.getItemAtPosition(position);
                        if(interiorData.category.equals(COMMUNITY_DATA)){
                            Intent i = new Intent(getContext(), CommunityContentActivity.class);
                            i.putExtra(CommunityContentActivity.EXTRA_POSTID_MESSAGE,interiorData.post_id);
                            startActivity(i);
                        }else {
                            Intent intent = new Intent(getContext(), InteriorActivity.class);
                            intent.putExtra(InteriorActivity.EXTRA_POST_ID_MESSAGE, interiorData.post_id);
                            intent.putExtra(InteriorActivity.EXTRA_CATEGORY_MESSAGE, interiorData.category);
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        MyPostData data = (MyPostData) gridView.getItemAtPosition(position);
                        Intent i = new Intent(getContext(), CommunityContentActivity.class);
                        i.putExtra(CommunityContentActivity.EXTRA_POSTID_MESSAGE, data.post_id);
                        startActivity(i);
                }
            }
        });
        Button btn = (Button) view.findViewById(R.id.btn_notify);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NotificationActivity.class));
            }
        });
        return view;
    }

    MyProfileData data;

    private void setMyPage(MyProfileData data) {
        this.data = data;
        nameView.setText(data.getName());
        scrapView.setText("" + data.myscrap_count);
        myPostView.setText("" + data.mypost_count);
        Glide.with(getContext())
                .load(data.getProfileImage())
                .into(profileView);
    }

    @Override
    public void onResume() {
        super.onResume();
        NetworkManager.getInstance().getMypage(getContext(), new NetworkManager.OnResultListener<MyProfileData>() {
            @Override
            public void onSuccess(Request request, MyProfileData result) {
                setMyPage(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
        if (tabLayout.getSelectedTabPosition() == 0) {
            gridView.setAdapter(scrapAdapter);
            scrapAdapter.clear();
            NetworkManager.getInstance().getMyScrap(getContext(), new NetworkManager.OnResultListener<MyPageScrap>() {
                @Override
                public void onSuccess(Request request, MyPageScrap result) {
                    scrapAdapter.addAll(result.result.scrapData.postList);
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } else {
            gridView.setAdapter(postAdapter);
            postAdapter.clear();
            NetworkManager.getInstance().getMyPost(getContext(), new NetworkManager.OnResultListener<MyPost>() {
                @Override
                public void onSuccess(Request request, MyPost result) {
                    postAdapter.addAll(result.result.postData.postList);
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode != Activity.RESULT_CANCELED) {
            Uri selectedImageUri = data.getData();
            Cursor c = getContext().getContentResolver().query(selectedImageUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
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
                    Toast.makeText(getContext(), result.result.message, Toast.LENGTH_SHORT).show();
                    NetworkManager.getInstance().getMypage(getContext(), new NetworkManager.OnResultListener<MyProfileData>() {
                        @Override
                        public void onSuccess(Request request, MyProfileData result) {
                            setMyPage(result);
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
            Glide.with(this).load(selectedImageUri).into(profileView);
        }
    }
}
