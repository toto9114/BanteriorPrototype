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
import com.example.sony.banteriorprototype.data.MainPage.MainContent;
import com.example.sony.banteriorprototype.data.MainPage.MainData;
import com.example.sony.banteriorprototype.main.MainInterior.DetailInterior.InteriorActivity;

import okhttp3.Request;


/**
 * A simple {@link Fragment} subclass.
 */
public class
        InteriorFragment extends Fragment {

    public InteriorFragment() {
        // Required empty public constructor
    }

    ListView listView;
    MainInteriorAdapter mAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_interior, container, false);
        listView = (ListView) view.findViewById(R.id.interior_listView);
        mAdapter = new MainInteriorAdapter();
        listView.setAdapter(mAdapter);

        NetworkManager.getInstance().getMainpage(getContext(), new NetworkManager.OnResultListener<MainData>() {
            @Override
            public void onSuccess(Request request, MainData result) {
                for(MainContent data : result.mainList) {
                    mAdapter.addMainInterior(data);
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainContent data = (MainContent)listView.getItemAtPosition(position);
                String category = data.category;
                Intent intent = new Intent(getContext(),InteriorActivity.class);
                intent.putExtra(InteriorActivity.EXTRA_CATEGORY_MESSAGE,category);
                startActivity(intent);
            }
        });
        return view;
    }
}
