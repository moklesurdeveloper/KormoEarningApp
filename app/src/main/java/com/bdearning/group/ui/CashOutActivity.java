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

public class CashOutActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView rocket,bkash,nagad,upai;
    private CardView numberCardView,AmountCardView,payment_method_CardView;
    private EditText numberEt,amountEt;
    private TextView cashOutRequest,cash_out_message;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private String paymentMethodName = "";
    private String paymentMethodID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_out);


        toolbar = findViewById(R.id.toolbarWork);
        setSupportActionBar(toolbar);

        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        getSupportActionBar().setTitle(getString(R.string.cash_out_txt));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


       // TextView
        cashOutRequest = findViewById(R.id.cash_out_request);
        cash_out_message = findViewById(R.id.cash_out_message);
        //Image view
        rocket = findViewById(R.id.rocket_cashOut);
        bkash = findViewById(R.id.bkash_cashOut);
        nagad = findViewById(R.id.nagad_cashOut);
        upai = findViewById(R.id.upai_cashOut);

        // cardView
        numberCardView = findViewById(R.id.amount_transer);
        AmountCardView = findViewById(R.id.amount_card_cashOut);
        payment_method_CardView = findViewById(R.id.payment_method_CardView);
        
        numberEt = findViewById(R.id.number_cashOut);
        amountEt= findViewById(R.id.amount_cashOut);




        numberCardView.setVisibility(View.GONE);
        AmountCardView.setVisibility(View.GONE);

        rocket.setOnClickListener(this::onClick);
        bkash.setOnClickListener(this::onClick);
        nagad.setOnClickListener(this::onClick);
        upai.setOnClickListener(this::onClick);
        cashOutRequest.setOnClickListener(this::onClick);


        cash_out_message.setText(sharedPreferences.getString("cash_out_message",""));
    }



    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch (id)
        {
            case R.id.rocket_cashOut:
                rocketWork();
                break;
            case R.id.bkash_cashOut:
                bkashWork();
                break;
            case R.id.nagad_cashOut:
                nagadWork();
                break;
            case R.id.upai_cashOut:
                upaiWork();
                break;
            case R.id.cash_out_request:
                cash_out_request();
                break;
        }
    }

    private void cash_out_request()
    {
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
                map.put("account_info",numberEt.getText().toString().trim());
                map.put("amount",amountEt.getText().toString().trim());
                map.put("method_name",paymentMethodName);
                map.put("method_id",paymentMethodID);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void rocketWork()
    {
        bkash.setBackgroundColor(Color.WHITE);
        nagad.setBackgroundColor(Color.WHITE);
        rocket.setBackgroundColor(Color.BLACK);
        upai.setBackgroundColor(Color.WHITE);

        numberCardView.setVisibility(View.VISIBLE);
        AmountCardView.setVisibility(View.VISIBLE);

        //payment_method_CardView hide
        payment_method_CardView.setVisibility(View.GONE);
        paymentMethodName = "Rocket";
        paymentMethodID = "1";
        Toast.makeText(getApplicationContext(),paymentMethodName , Toast.LENGTH_SHORT).show();
    }
    private void bkashWork()
    {
        bkash.setBackgroundColor(Color.BLACK);
        nagad.setBackgroundColor(Color.WHITE);
        rocket.setBackgroundColor(Color.WHITE);
        upai.setBackgroundColor(Color.WHITE);

        numberCardView.setVisibility(View.VISIBLE);
        AmountCardView.setVisibility(View.VISIBLE);

        //payment_method_CardView hide
        payment_method_CardView.setVisibility(View.GONE);
        paymentMethodName = "Bkash";
        paymentMethodID = "2";
        Toast.makeText(getApplicationContext(),paymentMethodName , Toast.LENGTH_SHORT).show();
    }
    private void nagadWork()
    {
        bkash.setBackgroundColor(Color.WHITE);
        nagad.setBackgroundColor(Color.BLACK);
        rocket.setBackgroundColor(Color.WHITE);
        upai.setBackgroundColor(Color.WHITE);

        numberCardView.setVisibility(View.VISIBLE);
        AmountCardView.setVisibility(View.VISIBLE);

        //payment_method_CardView hide
        payment_method_CardView.setVisibility(View.GONE);
        paymentMethodName = "Nagad";
        paymentMethodID = "3";
        Toast.makeText(getApplicationContext(),paymentMethodName , Toast.LENGTH_SHORT).show();
    }
    private void upaiWork()
    {
        bkash.setBackgroundColor(Color.WHITE);
        nagad.setBackgroundColor(Color.WHITE);
        rocket.setBackgroundColor(Color.WHITE);
        upai.setBackgroundColor(Color.BLACK);

        numberCardView.setVisibility(View.VISIBLE);
        AmountCardView.setVisibility(View.VISIBLE);

        //payment_method_CardView hide
        payment_method_CardView.setVisibility(View.GONE);

        paymentMethodName = "Upai";
        paymentMethodID = "4";
        Toast.makeText(getApplicationContext(),paymentMethodName , Toast.LENGTH_SHORT).show();
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