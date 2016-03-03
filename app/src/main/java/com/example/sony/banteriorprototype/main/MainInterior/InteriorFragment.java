package com.example.sony.banteriorprototype.main.MainInterior;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.InteriorData;
import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.main.MainInterior.DetailInterior.InteriorActivity;

import java.util.List;


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
//        NetworkManager.getInstance().getMainInteriorData(new NetworkManager.OnResultListener<List<InteriorData>>() {
//            @Override
//            public void onSuccess(List<InteriorData> result) {
//                for(InteriorData data : result){
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
//    private void initdata(){
//        for(int i = 0 ; i< 4; i++){
//            InteriorData data= new InteriorData();
//            data.interiorImage = MAIN_INTERIOR_IMAGE[i];
//            data.interiorType = i;
//            Log.i("InteriorFragment","i: "+i);
//            mAdapter.addMainInterior(data);
//        }
//    }
}
