package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;
import com.example.sony.banteriorprototype.data.PostTypeResult;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailInteriorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailInteriorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    public static final String EXTRA_INTERIOR = "interior";
    public static final String EXTRA_POSTION = "position";


//    private static final int[] MAIN_INTERIOR_IMAGE = {R.drawable.modern_main1,
//            R.drawable.modern_main2,
//            R.drawable.modern_main3,
//            R.drawable.modern_main4};
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

    InteriorContentData interiorContentData;
    int position;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            interiorContentData = (InteriorContentData)b.getSerializable(EXTRA_INTERIOR);
             position = b.getInt(EXTRA_POSTION);
            //((InteriorActivity)getActivity()).setProductData(interiorContentData.productDataList);
        }
    }

    ImageView interiorView;
    ImageView shareView;
    Button scrapView;

    boolean isScrap= false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_detail_interior, container, false);
        interiorView = (ImageView)view.findViewById(R.id.image_detail_interior);
//        textView = (TextView)view.findViewById(R.id.text_test);
        scrapView = (Button)view.findViewById(R.id.image_scrap);
        shareView = (ImageView)view.findViewById(R.id.image_share);

//        textView.setText(interiorContentData.category);
        scrapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isScrap){
                    scrapView.setSelected(true);
                    isScrap = false;
                }
                else {
                    scrapView.setSelected(false);
                    isScrap = true;
                }
                NetworkManager.getInstance().doScrap(getActivity(), interiorContentData.post_id, new NetworkManager.OnResultListener<PostTypeResult>() {
                    @Override
                    public void onSuccess(Request request, PostTypeResult result) {
                        Toast.makeText(getContext(),result.result.message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
            }
        });
        Glide.with(getContext()).load(interiorContentData.interiorImage).into(interiorView);

        return view;
    }

}
