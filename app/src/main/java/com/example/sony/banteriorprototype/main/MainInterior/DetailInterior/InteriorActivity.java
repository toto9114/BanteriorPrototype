package com.example.sony.banteriorprototype.main.MainInterior.DetailInterior;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.ProductData;

public class InteriorActivity extends AppCompatActivity {
    private static final int[] DETAIL_INTERIOR_MODERN1 = {R.drawable.detail_modern1,
            R.drawable.detail_modern2,
            R.drawable.detail_modern3,
            R.drawable.detail_modern4};
    //SliderLayout sliderLayout;
    ViewPager pager;
    InteriorPagerAdapter imageAdapter;
    ProductListAdapter productAdapter;
    ListView listView;
    int productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interior);

        pager = (ViewPager)findViewById(R.id.interior_pager);
        imageAdapter = new InteriorPagerAdapter(getSupportFragmentManager());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                productId = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setAdapter(imageAdapter);

        listView = (ListView)findViewById(R.id.product_listView);
        productAdapter = new ProductListAdapter();
        listView.setAdapter(productAdapter);

        Button btn = (Button)findViewById(R.id.btn_rent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InteriorActivity.this, RentalActivity.class));
            }
        });
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_detail_interior,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_show_interior){
            startActivity(new Intent(this,DetailInteriorListActivity.class));
            return true;
        }
        return false;
    }

    private void initData(){
        for(int i = 0 ; i<4; i++){
            ProductData data = new ProductData();
            data.productImage = DETAIL_INTERIOR_MODERN1[i];
            productAdapter.add(data);
        }
    }
}
