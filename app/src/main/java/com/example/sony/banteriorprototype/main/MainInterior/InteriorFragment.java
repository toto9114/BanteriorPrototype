package com.example.sony.banteriorprototype.main.MainInterior;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Interior.Interior;
import com.example.sony.banteriorprototype.main.MainInterior.DetailInterior.InteriorActivity;

import okhttp3.Request;


/**
 * A simple {@link Fragment} subclass.
 */
public class InteriorFragment extends Fragment {

    public InteriorFragment() {
        // Required empty public constructor
    }
    private static final int[] MAIN_INTERIOR_IMAGE = {R.drawable.modern_main1,
            R.drawable.modern_main2,
            R.drawable.modern_main3,
            R.drawable.modern_main4};
    ListView listView;
    MainInteriorAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_interior, container, false);
        listView = (ListView)view.findViewById(R.id.interior_listView);
        mAdapter = new MainInteriorAdapter();
        listView.setAdapter(mAdapter);
        NetworkManager.getInstance().getInteriorPost(getContext(), new NetworkManager.OnResultListener<Interior>() {
            @Override
            public void onSuccess(Request request, Interior result) {
                mAdapter.addAll(result.result.postData.postList);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
//        NetworkManager.getInstance().getMainInteriorData(new NetworkManager.OnResultListener<List<InteriorContentData>>() {
//            @Override
//            public void onSuccess(List<InteriorContentData> result) {
//                for(InteriorContentData data : result){
//                    mAdapter.addMainInterior(data);
//                }
//            }
//
//            @Override
//            public void onFailure(int code) {
//
//            }
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(), InteriorActivity.class));
            }
        });
        return view;
    }
}
