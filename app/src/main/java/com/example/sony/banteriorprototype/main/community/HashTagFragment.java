package com.example.sony.banteriorprototype.main.community;


import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sony.banteriorprototype.R;
import com.wefika.flowlayout.FlowLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class HashTagFragment extends Fragment {


    public HashTagFragment() {
        // Required empty public constructor
    }

    EditText keywordView;
    FlowLayout mFlowlayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_hash_tag, container, false);
        ActionBar actionBar = ((WriteActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        keywordView = (EditText)view.findViewById(R.id.edit_hash);
        mFlowlayout = (FlowLayout)view.findViewById(R.id.flowlayout);
        Button btn = (Button)view.findViewById(R.id.btn_add);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = keywordView.getText().toString();
                TextView hash = new TextView(getContext());
                hash.setText(keyword);
                mFlowlayout.addView(hash);
                Toast.makeText(getContext(),"add",Toast.LENGTH_SHORT).show();
            }
        });
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
            return true;
        }
        if(id == android.R.id.home){
            ((WriteActivity)getActivity()).getSupportFragmentManager()
                    .popBackStack();
        }
        return false;
    }
}
