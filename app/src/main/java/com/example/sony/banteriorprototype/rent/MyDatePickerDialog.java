package com.example.sony.banteriorprototype.rent;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.sony.banteriorprototype.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDatePickerDialog extends DialogFragment {


    public MyDatePickerDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    NumberPicker yearPicker;
    NumberPicker monthPicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_date_picker_dialog, container, false);
        yearPicker = (NumberPicker)view.findViewById(R.id.numberPicker_year);
        monthPicker = (NumberPicker)view.findViewById(R.id.numberPicker_month);
        Button btn = (Button)view.findViewById(R.id.btn_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn = (Button)view.findViewById(R.id.btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RentalActivity)getActivity()).setDate(yearPicker.getValue(),monthPicker.getValue());
                dismiss();
            }
        });
        initDate();
        return view;
    }
    private void initDate(){
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(2018);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
    }


}
