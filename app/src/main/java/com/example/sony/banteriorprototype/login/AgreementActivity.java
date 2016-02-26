package com.example.sony.banteriorprototype.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.sony.banteriorprototype.R;

public class AgreementActivity extends AppCompatActivity {
    CheckBox checkService,checkPersonal;
    public static final int SERVICE_CHECK = 0;
    public static final int PERSONAL_CHECK = 1;

    boolean[] check;
    public static final String EXTRA_MESSAGE = "message";

    public static final String RESULT_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        checkService = (CheckBox)findViewById(R.id.check_service);
        checkPersonal = (CheckBox)findViewById(R.id.check_personal);
        Intent intent = getIntent();

        check = intent.getBooleanArrayExtra(EXTRA_MESSAGE);
        checkService.setChecked(check[SERVICE_CHECK]);
        checkPersonal.setChecked(check[PERSONAL_CHECK]);

        checkService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    check[SERVICE_CHECK] = isChecked;

//                if(isServiceChecked&&isPersonalChecked){
//                    finish();
//                }
            }
        });
        checkPersonal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check[PERSONAL_CHECK] = isChecked;
//                if(isServiceChecked&&isPersonalChecked){
//                    setResult(Activity.RESULT_OK,data);
//                    finish();
//                }
            }
        });

        Intent data = new Intent();
        data.putExtra(RESULT_DATA, check);
        setResult(Activity.RESULT_OK, data);

    }
}
