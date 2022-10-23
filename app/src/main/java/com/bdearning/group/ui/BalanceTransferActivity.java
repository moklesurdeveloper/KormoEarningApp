package com.bdearning.group.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
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

public class BalanceTransferActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private EditText email,amount;
    private TextView send;


    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_transfer);

        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();



        toolbar = findViewById(R.id.toolbarTransfer);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.tk_pathan_txt));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();


       // Toast.makeText(this, ""+amount.getText().toString(), Toast.LENGTH_SHORT).show();

            send.setOnClickListener(v ->
            {


                if (Integer.parseInt(amount.getText().toString()) >= 200){
                
                StringRequest request = new StringRequest(Request.Method.POST, Constant.BALANCE_TRANSFER, response -> {

                    try {
                        JSONObject object = new JSONObject(response);
                        Toast.makeText(this, object.getString("message"), Toast.LENGTH_SHORT).show();
                        if (object.getBoolean("success")) {

                            email.setText("");
                            amount.setText("");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
                    error.printStackTrace();
                    Log.e("121121888",error.getMessage());

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
                        map.put("email", email.getText().toString().trim());
                        map.put("amount", amount.getText().toString().trim());
                        return map;
                    }
                
                };

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);
            }
                else 
                {
                    Toast.makeText(this, "Minimum amount 200", Toast.LENGTH_SHORT).show();
                }
            }
            );
            
    }

    public void init()
    {
        email = findViewById(R.id.email_transferActivity);
        amount  = findViewById(R.id.amount_transferActivity);
        send = findViewById(R.id.send_transfer);

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