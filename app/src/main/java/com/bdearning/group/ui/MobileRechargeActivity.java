package com.bdearning.group.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bdearning.group.Constant;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bdearning.kormoappp.R;

public class MobileRechargeActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private CardView paymentText, number, amount,select_operatorsCardView;
    private ImageView bl, gp, robi, airtel, teletalk, skitto;
    private EditText numberEt, amountEt;
    private TextView request_recharge,recharge_message;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private String operatorsName = "";
    private String operatorsID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_rechage);

        toolbar = findViewById(R.id.toolbar_rechage);
        setSupportActionBar(toolbar);

        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getSupportActionBar().setTitle(getString(R.string.mobile_txt));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // TextView
        request_recharge = findViewById(R.id.request_recharge);
        recharge_message = findViewById(R.id.recharge_message);
        //EditText
        numberEt = findViewById(R.id.et_number_recharage);
        amountEt = findViewById(R.id.et_amount_rechage);

        //CardView
        paymentText = findViewById(R.id.payment_info_recharge);
        number = findViewById(R.id.number_recharge);
        amount = findViewById(R.id.amount_recharge);
        select_operatorsCardView = findViewById(R.id.select_operatorsCardView);

        //ImageView
        bl = findViewById(R.id.bl_mobile_recharge);
        gp = findViewById(R.id.gp_mobile_recharge);
        robi = findViewById(R.id.robi_mobile_recharge);
        airtel = findViewById(R.id.airtel_mobile_recharge);
        teletalk = findViewById(R.id.teletalk_mobile_recharge);
        skitto = findViewById(R.id.skitto_mobile_recharge);

        bl.setOnClickListener(this::onClick);
        gp.setOnClickListener(this::onClick);
        robi.setOnClickListener(this::onClick);
        airtel.setOnClickListener(this::onClick);
        teletalk.setOnClickListener(this::onClick);
        skitto.setOnClickListener(this::onClick);
        request_recharge.setOnClickListener(this::onClick);

        number.setVisibility(View.GONE);
        amount.setVisibility(View.GONE);


        recharge_message.setText(sharedPreferences.getString("recharge_message",""));

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //,,,,teletalk,skitto
            case R.id.bl_mobile_recharge:
                blWork();
                break;
            case R.id.gp_mobile_recharge:
                gpWork();
                break;
            case R.id.robi_mobile_recharge:
                robiWork();
                break;
            case R.id.airtel_mobile_recharge:
                airtelWork();
                break;
            case R.id.teletalk_mobile_recharge:
                teletalkWork();
                break;
            case R.id.skitto_mobile_recharge:
                skittoWork();
                break;
            case R.id.request_recharge:
                requestRecharge();
                break;
        }
    }


    private void requestRecharge() {

        StringRequest request = new StringRequest(Request.Method.POST, Constant.WITHDRAW_REQUEST, response -> {

            try {
                JSONObject object = new JSONObject(response);
                Toast.makeText(this, object.getString("message"), Toast.LENGTH_SHORT).show();
                if (object.getBoolean("success")) {

                    numberEt.setText("");
                    amountEt.setText("");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        }) {
            //add token to headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "bearer " + token);
                return map;
            }

            //add params
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("account_info", numberEt.getText().toString());
                map.put("amount", amountEt.getText().toString());
                map.put("method_name", operatorsName);
                map.put("method_id", "5");
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
    private void gpWork() {
        operatorsName = "Grameen";
        operatorsID = "1";
        Toast.makeText(getApplicationContext(), operatorsName, Toast.LENGTH_SHORT).show();

        bl.setBackgroundColor(Color.WHITE);
        gp.setBackgroundColor(Color.BLACK);
        robi.setBackgroundColor(Color.WHITE);
        airtel.setBackgroundColor(Color.WHITE);
        teletalk.setBackgroundColor(Color.WHITE);
        skitto.setBackgroundColor(Color.WHITE);

        number.setVisibility(View.VISIBLE);
        amount.setVisibility(View.VISIBLE);

        // select_operatorsCardView hide
        select_operatorsCardView.setVisibility(View.GONE);
    }
    private void blWork() {
        operatorsName = "banglalink";
        operatorsID = "2";
        Toast.makeText(getApplicationContext(), operatorsName, Toast.LENGTH_SHORT).show();

        bl.setBackgroundColor(Color.BLACK);
        gp.setBackgroundColor(Color.WHITE);
        robi.setBackgroundColor(Color.WHITE);
        airtel.setBackgroundColor(Color.WHITE);
        teletalk.setBackgroundColor(Color.WHITE);
        skitto.setBackgroundColor(Color.WHITE);

        number.setVisibility(View.VISIBLE);
        amount.setVisibility(View.VISIBLE);

        // select_operatorsCardView hide
        select_operatorsCardView.setVisibility(View.GONE);
    }
    private void robiWork() {
        operatorsName = "Robi";
        operatorsID = "3";
        Toast.makeText(getApplicationContext(), operatorsName, Toast.LENGTH_SHORT).show();

        bl.setBackgroundColor(Color.WHITE);
        gp.setBackgroundColor(Color.WHITE);
        robi.setBackgroundColor(Color.BLACK);
        airtel.setBackgroundColor(Color.WHITE);
        teletalk.setBackgroundColor(Color.WHITE);
        skitto.setBackgroundColor(Color.WHITE);


        number.setVisibility(View.VISIBLE);
        amount.setVisibility(View.VISIBLE);

        // select_operatorsCardView hide
        select_operatorsCardView.setVisibility(View.GONE);
    }
    private void teletalkWork() {
        operatorsName = "Teletalk";
        operatorsID = "4";
        Toast.makeText(getApplicationContext(), operatorsName, Toast.LENGTH_SHORT).show();

        bl.setBackgroundColor(Color.WHITE);
        gp.setBackgroundColor(Color.WHITE);
        robi.setBackgroundColor(Color.WHITE);
        airtel.setBackgroundColor(Color.WHITE);
        teletalk.setBackgroundColor(Color.BLACK);
        skitto.setBackgroundColor(Color.WHITE);

        number.setVisibility(View.VISIBLE);
        amount.setVisibility(View.VISIBLE);

        // select_operatorsCardView hide
        select_operatorsCardView.setVisibility(View.GONE);
    }
    private void airtelWork() {
        operatorsName = "Airtel";
        operatorsID = "5";
        Toast.makeText(getApplicationContext(), operatorsName, Toast.LENGTH_SHORT).show();

        bl.setBackgroundColor(Color.WHITE);
        gp.setBackgroundColor(Color.WHITE);
        robi.setBackgroundColor(Color.WHITE);
        airtel.setBackgroundColor(Color.BLACK);
        teletalk.setBackgroundColor(Color.WHITE);
        skitto.setBackgroundColor(Color.WHITE);

        number.setVisibility(View.VISIBLE);
        amount.setVisibility(View.VISIBLE);

        // select_operatorsCardView hide
        select_operatorsCardView.setVisibility(View.GONE);
    }
    private void skittoWork() {
        operatorsName = "Skitto";
        operatorsID = "6";
        Toast.makeText(getApplicationContext(), operatorsName, Toast.LENGTH_SHORT).show();

        bl.setBackgroundColor(Color.WHITE);
        gp.setBackgroundColor(Color.WHITE);
        robi.setBackgroundColor(Color.WHITE);
        airtel.setBackgroundColor(Color.WHITE);
        teletalk.setBackgroundColor(Color.WHITE);
        skitto.setBackgroundColor(Color.BLACK);

        number.setVisibility(View.VISIBLE);
        amount.setVisibility(View.VISIBLE);

        // select_operatorsCardView hide
        select_operatorsCardView.setVisibility(View.GONE);
    }







    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() ==android.R.id.home)
        {
            goBackHome();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        goBackHome();
    }

    private void goBackHome()
    {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();
    }
}