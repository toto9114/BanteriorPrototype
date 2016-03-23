package com.example.sony.banteriorprototype.rent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.main.community.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sony on 2016-03-02.
 */
public class OrderProductOtherViewHolder extends RecyclerView.ViewHolder {

    Button cardBtn;
    Button phoneBtn;
    Button checkBtn;
    EditText phoneView, addressView1, addressView2, DetailAddressView, yearView, monthView;
    TextView todayView;
    ImageView calenderView;
    Context mContext;
    TextView fieldPrice,totalPriceVIew,priceView;
    public OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public OrderProductOtherViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        phoneView = (EditText) itemView.findViewById(R.id.edit_phone);
        addressView1 = (EditText) itemView.findViewById(R.id.edit_adderess1);
        addressView2 = (EditText) itemView.findViewById(R.id.edit_address2);
        DetailAddressView = (EditText) itemView.findViewById(R.id.edit_detail_address);
        yearView = (EditText) itemView.findViewById(R.id.edit_year);
        monthView = (EditText) itemView.findViewById(R.id.edit_month);
        fieldPrice = (TextView)itemView.findViewById(R.id.text_field_price);
        totalPriceVIew = (TextView)itemView.findViewById(R.id.text_total_price);
        priceView = (TextView)itemView.findViewById(R.id.text_price);


        todayView = (TextView) itemView.findViewById(R.id.text_today);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
        String ds = sdf.format(new Date());
        todayView.setText(ds);

        cardBtn = (Button) itemView.findViewById(R.id.btn_card);
        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);
                phoneBtn.setSelected(false);
                checkBtn.setSelected(false);
            }
        });
        phoneBtn = (Button) itemView.findViewById(R.id.btn_phone);
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);
                cardBtn.setSelected(false);
                checkBtn.setSelected(false);
            }
        });

        checkBtn = (Button) itemView.findViewById(R.id.btn_check);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);
                phoneBtn.setSelected(false);
                cardBtn.setSelected(false);
            }
        });

        calenderView = (ImageView) itemView.findViewById(R.id.image_calender);
        calenderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v);
                }
            }
        });
        init();
    }
    private void init(){
        Calendar c= Calendar.getInstance();
        yearView.setText(""+c.get(Calendar.YEAR));
        monthView.setText(""+c.get(Calendar.MONTH));
    }
    int price;
    public void setPrice(int price){
        this.price = price;
        priceView.setText(""+price);
    }
    int year, month;

    private static final int DEPOSIT_PRICE = 50000;
    private static final int FEE_PRICE = 20000;
    private static final String TEXT_FIELD_PRICE = "상품금액";
    public void setDate(int year, int month) {
        this.year = year;
        this.month = month;
        yearView.setText("" + year);
        monthView.setText("" + month);
        Calendar c = Calendar.getInstance();
        int diff = (year- c.get(Calendar.YEAR))*12 + (month-c.get(Calendar.MONTH));
        fieldPrice.setText("");
        fieldPrice.setText(TEXT_FIELD_PRICE+"(x"+diff+"개월)");
        priceView.setText(""+price*diff);
        int totalPrice = price*diff + DEPOSIT_PRICE + FEE_PRICE;
        totalPriceVIew.setText(""+totalPrice);
    }
}
