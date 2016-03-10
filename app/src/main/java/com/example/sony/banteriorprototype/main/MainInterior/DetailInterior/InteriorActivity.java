package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Interior.InteriorResult;
import com.example.sony.banteriorprototype.rent.RentalActivity;

import okhttp3.Request;

public class InteriorActivity extends AppCompatActivity {
    ViewPager pager;
    InteriorPagerAdapter imageAdapter;
    ProductListAdapter productAdapter;

    ListView listView;
    int productId;
    public static final String EXTRA_INTERIOR_MESSAGE = "interior";
    public static final String EXTRA_CATEGORY_MESSAGE = "category";
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interior);
        category = getIntent().getStringExtra(EXTRA_CATEGORY_MESSAGE);
        int postId = getIntent().getIntExtra(EXTRA_INTERIOR_MESSAGE, 0);
        NetworkManager.getInstance().getInteriorPostList(this, category, new NetworkManager.OnResultListener<InteriorResult>() {
            @Override
            public void onSuccess(Request request, InteriorResult result) {
                imageAdapter.addAll(result.postData.interiorList);
                int postId = result.postData.interiorList.get(0).post_id;
                NetworkManager.getInstance().getInteriorPost(InteriorActivity.this, postId, category, new NetworkManager.OnResultListener<InteriorResult>() {
                    @Override
                    public void onSuccess(Request request, InteriorResult result) {
                        productAdapter.clear();
                        productAdapter.addAll(result.detailData.productDataList);
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

        pager = (ViewPager) findViewById(R.id.interior_pager);
        imageAdapter = new InteriorPagerAdapter(getSupportFragmentManager());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                productAdapter.clear();
                productId = position;
                NetworkManager.getInstance().getInteriorPost(InteriorActivity.this, 1, category, new NetworkManager.OnResultListener<InteriorResult>() {
                    @Override
                    public void onSuccess(Request request, InteriorResult result) {
                        productAdapter.addAll(result.detailData.productDataList);
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
//                for (ProductData data : imageAdapter.getInterior(position).productDataList) {
//                    productAdapter.add(data);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        pager.setAdapter(imageAdapter);
        listView = (ListView) findViewById(R.id.product_listView);
        productAdapter = new ProductListAdapter();
        listView.setAdapter(productAdapter);

        Button btn = (Button) findViewById(R.id.btn_rent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InteriorActivity.this, RentalActivity.class);
                i.putExtra(RentalActivity.EXTRA_PRODUCT_MESSAGE, imageAdapter.getInterior(productId));
                startActivity(i);
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
            intent.putExtra(DetailInteriorListActivity.EXTRA_CATEGORY_MESSAGE,category);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
