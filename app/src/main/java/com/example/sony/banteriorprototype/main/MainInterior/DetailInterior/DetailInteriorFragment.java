package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sony.banteriorprototype.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailInteriorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailInteriorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static final int[] MAIN_INTERIOR_IMAGE = {R.drawable.modern_main1,
            R.drawable.modern_main2,
            R.drawable.modern_main3,
            R.drawable.modern_main4};
    // TODO: Rename and change types of parameters
    private int imageId;



    public DetailInteriorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DetailInteriorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailInteriorFragment newInstance(int param1) {
        DetailInteriorFragment fragment = new DetailInteriorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageId = getArguments().getInt(ARG_PARAM1);
        }
    }

    ImageView interiorView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_detail_interior, container, false);
        interiorView = (ImageView)view.findViewById(R.id.image_detail_interior);
        interiorView.setImageResource(MAIN_INTERIOR_IMAGE[imageId]);
        return view;
    }

}
