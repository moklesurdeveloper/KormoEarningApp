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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ManeyAddActivity extends AppCompatActivity {

    private ImageView bkash,nagad,roket ,upai;
    private Toolbar toolbar;
    private LinearLayout linearLayout_EditText;
    private EditText addAmount,paymentNumber,TnsId,loginNumber;
    private TextView sendRequest,messageText,paymentNumberListText;
    private CardView select_add_money_CardView;


    private SharedPreferences sharedPreferences;
    private String methodName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maney_add);

        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        toolbar = findViewById(R.id.toolbarAddManey);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.koto_tk_add));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       // EditText
        addAmount = findViewById(R.id.add_amount_add_money);
        paymentNumber = findViewById(R.id.payment_number_add_money);
        TnsId = findViewById(R.id.trns_id_add_money);
        loginNumber = findViewById(R.id.login_number_add_money);

        sendRequest = findViewById(R.id.send_request__add_money);
        messageText = findViewById(R.id.message__add_money);
        paymentNumberListText = findViewById(R.id.payment_numberList__add_money);

        bkash = findViewById(R.id.bkash_add_maney);
        nagad = findViewById(R.id.nagad_add_maney);
        upai = findViewById(R.id.upai_add_maney);
        roket = findViewById(R.id.roket_add_maney);
        linearLayout_EditText = findViewById(R.id.add_money_all_editText);

        select_add_money_CardView = findViewById(R.id.select_add_money_CardView);


        linearLayout_EditText.setVisibility(View.GONE);

        messageText.setText(sharedPreferences.getString("add_money_message",""));
        paymentNumberListText.setText(sharedPreferences.getString("payment_number_message",""));


        sendRequest.setOnClickListener(v ->
        {
            addPaymentRequest();
        });

        bkash.setOnClickListener(v ->
        {
            methodName = "Bkash";
            Toast.makeText(getApplicationContext(), methodName, Toast.LENGTH_SHORT).show();
            bkash.setBackgroundColor(Color.BLACK);
            nagad.setBackgroundColor(Color.WHITE);
            roket.setBackgroundColor(Color.WHITE);
            upai.setBackgroundColor(Color.WHITE);

            linearLayout_EditText.setVisibility(View.VISIBLE);

            //select_add_money_CardView hide
            select_add_money_CardView.setVisibility(View.GONE);
        });
        nagad.setOnClickListener(v ->
        {
            methodName = "Nagad";
            Toast.makeText(getApplicationContext(), methodName, Toast.LENGTH_SHORT).show();

            roket.setBackgroundColor(Color.WHITE);
            bkash.setBackgroundColor(Color.WHITE);
            nagad.setBackgroundColor(Color.BLACK);
            upai.setBackgroundColor(Color.WHITE);

            linearLayout_EditText.setVisibility(View.VISIBLE);

            //select_add_money_CardView hide
            select_add_money_CardView.setVisibility(View.GONE);
        });
        roket.setOnClickListener(v ->
        {
            methodName = "Roket";
            Toast.makeText(getApplicationContext(), methodName, Toast.LENGTH_SHORT).show();

            roket.setBackgroundColor(Color.BLACK);
            bkash.setBackgroundColor(Color.WHITE);
            nagad.setBackgroundColor(Color.WHITE);
            upai.setBackgroundColor(Color.WHITE);

            linearLayout_EditText.setVisibility(View.VISIBLE);

            //select_add_money_CardView hide
            select_add_money_CardView.setVisibility(View.GONE);
        });
        upai.setOnClickListener(v ->
        {
            methodName = "Upai";
            Toast.makeText(getApplicationContext(), methodName, Toast.LENGTH_SHORT).show();

            roket.setBackgroundColor(Color.WHITE);
            bkash.setBackgroundColor(Color.WHITE);
            nagad.setBackgroundColor(Color.WHITE);
            upai.setBackgroundColor(Color.BLACK);

            linearLayout_EditText.setVisibility(View.VISIBLE);

            //select_add_money_CardView hide
            select_add_money_CardView.setVisibility(View.GONE);
        });


    }

    private void addPaymentRequest()
    {

        StringRequest request = new StringRequest(Request.Method.POST, Constant.ADD_MONEY_WALLET, response -> {

            try {
                JSONObject object = new JSONObject(response);
                Toast.makeText(this, object.getString("message"), Toast.LENGTH_SHORT).show();
                if (object.getBoolean("success")) {

                    addAmount.setText("");
                    paymentNumber.setText("");
                    TnsId.setText("");
                    loginNumber.setText("");
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
                map.put("amount", addAmount.getText().toString().trim());
                map.put("account_no", paymentNumber.getText().toString().trim());
                map.put("method_name", methodName);
                map.put("transaction_no", TnsId.getText().toString().trim());
                Log.e("1458585",methodName);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
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