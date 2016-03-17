package com.example.sony.banteriorprototype.login;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sony.banteriorprototype.Manager.NetworkManager;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.PostTypeResult;
import com.example.sony.banteriorprototype.main.MainActivity;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {


    public SignupFragment() {
        // Required empty public constructor
    }

    private static final int REQUEST_CODE = 0;

    EditText idView;
    EditText nameView;
    EditText passwordView;
    CheckBox serviceCheck, personalCheck;
    TextView serviceView, personalView;

    boolean[] check = {false, false};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        idView = (EditText) view.findViewById(R.id.edit_id);
        nameView = (EditText) view.findViewById(R.id.edit_name);
        passwordView = (EditText) view.findViewById(R.id.edit_password);
        serviceCheck = (CheckBox) view.findViewById(R.id.check_service);
        personalCheck = (CheckBox) view.findViewById(R.id.check_personal_info);


        serviceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check[AgreementActivity.SERVICE_CHECK] = isChecked;
            }
        });

        personalCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check[AgreementActivity.PERSONAL_CHECK] = isChecked;
            }
        });

        serviceView = (TextView) view.findViewById(R.id.text_service_agreement);
        serviceView.setText(Html.fromHtml("<u>" + serviceView.getText().toString() + "</u>"));
        personalView = (TextView) view.findViewById(R.id.text_personal_info_agreement);
        personalView.setText(Html.fromHtml("<u>" + personalView.getText().toString() + "</u>"));

        check[AgreementActivity.SERVICE_CHECK] = serviceCheck.isChecked();
        check[AgreementActivity.PERSONAL_CHECK] = personalCheck.isChecked();

        serviceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AgreementActivity.class);
                intent.putExtra(AgreementActivity.EXTRA_MESSAGE, check);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        personalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AgreementActivity.class);
                intent.putExtra(AgreementActivity.EXTRA_MESSAGE, check);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        Button btn = (Button) view.findViewById(R.id.btn_agree);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = idView.getText().toString();
                String name = nameView.getText().toString();
                String password = passwordView.getText().toString();
                NetworkManager.getInstance().signupUser(getContext(), name, email, password, new NetworkManager.OnResultListener<PostTypeResult>() {
                    @Override
                    public void onSuccess(Request request, PostTypeResult result) {
                        if (result.error == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage(result.result.message)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(getContext(), MainActivity.class));
                                            getActivity().finish();
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.i("AgreementActivity", "0");
            boolean[] result = data.getBooleanArrayExtra(AgreementActivity.RESULT_DATA);

            serviceCheck.setChecked(result[AgreementActivity.SERVICE_CHECK]);
            personalCheck.setChecked(result[AgreementActivity.PERSONAL_CHECK]);
        }
    }
}