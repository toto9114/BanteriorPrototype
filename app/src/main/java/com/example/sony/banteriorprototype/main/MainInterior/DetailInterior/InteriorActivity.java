package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Category;
import com.example.sony.banteriorprototype.data.Interior.InteriorContentData;
import com.example.sony.banteriorprototype.data.Interior.InteriorResult;
import com.example.sony.banteriorprototype.rent.RentalActivity;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class InteriorActivity extends AppCompatActivity {
    ViewPager pager;
    InteriorPagerAdapter imageAdapter;
    ProductListAdapter productAdapter;

    ListView listView;

    public static final String EXTRA_INTERIOR_MESSAGE = "interior";
    public static final String EXTRA_CATEGORY_MESSAGE = "category";
    public static final String EXTRA_POST_ID_MESSAGE = "postId";

    String category;
    int interiorPostion;
    int calledPostId = -1;
    int productId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interior);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_back);

        category = getIntent().getStringExtra(EXTRA_CATEGORY_MESSAGE);
        interiorPostion  = getIntent().getIntExtra(EXTRA_INTERIOR_MESSAGE, 0);
        calledPostId = getIntent().getIntExtra(EXTRA_POST_ID_MESSAGE,-1);

        pager = (ViewPager) findViewById(R.id.interior_pager);
        imageAdapter = new InteriorPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(imageAdapter);

        for(int i=0 ; i< Category.CATEGORY_ARRAY.length; i++){
            if(category.equals(Category.CATEGORY_ARRAY[i])) {
                toolbar.setLogo(Category.TITLE_IMAGE_ARRAY[i]);
                break;
            }
        }
        try {
            NetworkManager.getInstance().getInteriorPostList(this, category, new NetworkManager.OnResultListener<InteriorResult>() {
                @Override
                public void onSuccess(Request request, InteriorResult result) {
                    imageAdapter.addAll(result.postData.interiorList);
                    int postId;
                    if(calledPostId != -1){
                        postId = calledPostId;
                    }else {
                        postId = result.postData.interiorList.get(0).post_id;
                    //    productId =postId;
                    }
                    try {
                        NetworkManager.getInstance().getInteriorPost(InteriorActivity.this, postId, category, new NetworkManager.OnResultListener<InteriorResult>() {
                            @Override
                            public void onSuccess(Request request, InteriorResult result) {
                                productAdapter.clear();
                                productAdapter.addAll(result.detailData.productDataList);
                                pager.setCurrentItem(interiorPostion);
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                productAdapter.clear();
                InteriorContentData data = imageAdapter.getInterior(position);
                int postId = data.post_id;
                productId = position;
                try {
                    NetworkManager.getInstance().getInteriorPost(InteriorActivity.this, postId, category, new NetworkManager.OnResultListener<InteriorResult>() {
                        @Override
                        public void onSuccess(Request request, InteriorResult result) {
                            productAdapter.addAll(result.detailData.productDataList);
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//                for (ProductData data : imageAdapter.getInterior(position).productDataList) {
//                    productAdapter.add(data);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        listView = (ListView) findViewById(R.id.product_listView);
        productAdapter = new ProductListAdapter();
        listView.setAdapter(productAdapter);

        Button btn = (Button) findViewById(R.id.btn_rent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InteriorActivity.this, RentalActivity.class);
//                i.putExtra(RentalActivity.EXTRA_POST_ID_MESSAGE, imageAdapter.getInterior(productId).post_id);
//                i.putExtra(RentalActivity.EXTRA_CATEGORY_MESSAGE, imageAdapter.getInterior(productId).category);
                i.putExtra(RentalActivity.EXTRA_PRODUCT_MESSAGE,imageAdapter.getInterior(productId));
                startActivity(i);
            }
        });

        btn = (Button)findViewById(R.id.image_left);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager.getCurrentItem()>0) {
                    pager.setCurrentItem(pager.getCurrentItem() - 1);
                }
            }
        });

        btn = (Button)findViewById(R.id.image_right);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager.getCurrentItem()<imageAdapter.getCount()) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_detail_interior, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_show_interior) {
            Intent intent = new Intent(this, DetailInteriorListActivity.class);
            intent.putExtra(DetailInteriorListActivity.EXTRA_CATEGORY_MESSAGE, category);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
