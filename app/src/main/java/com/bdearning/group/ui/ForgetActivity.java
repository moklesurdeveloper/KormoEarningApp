package com.bdearning.group.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ForgetActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText forget_email_et,otp,newPassword_et,confirmPassword_et;
    private LinearLayout sendEmailLayout,resetPasswordLayout;
    private Button forgetBtn,resetPasswordBtn;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);


        progressBar = new ProgressDialog(getApplicationContext());
        //EditText
        forget_email_et = findViewById(R.id.forgetEmail);
        otp = findViewById(R.id.otp);
        newPassword_et = findViewById(R.id.newPassword);
        confirmPassword_et = findViewById(R.id.confirm_password);
        //LinearLayout
        sendEmailLayout = findViewById(R.id.email_send_Layout);
        resetPasswordLayout = findViewById(R.id.reset_Layout);
        //button

        forgetBtn = findViewById(R.id.forgetBtn);
        resetPasswordBtn = findViewById(R.id.reset_passwordBtn);

        sendEmailLayout.setVisibility(View.VISIBLE);
        resetPasswordLayout.setVisibility(View.GONE);

        sharedPreferences = getApplicationContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        forgetBtn.setOnClickListener(v ->
        {
            forget();
        });
        resetPasswordBtn.setOnClickListener(v ->
        {
            newPasswordSet();
        });
    }

    private void newPasswordSet()
    {

        StringRequest request = new StringRequest(Request.Method.POST,Constant.UPDATE_PASSWORD,response -> {
            try {
                JSONObject object = new JSONObject(response);
                Toast.makeText(getApplicationContext(), ""+object.getString("message"), Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "e "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        },error -> {
            error.printStackTrace();
            Toast.makeText(getApplicationContext(), "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token","");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","bearer "+token);
                return map;
            }
            // add params

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("otp",otp.getText().toString().trim());
                map.put("password",newPassword_et.getText().toString().trim());
                map.put("password_confirmation",confirmPassword_et.getText().toString().trim());
                return map;

            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void forget()
    {



        StringRequest request = new StringRequest(Request.Method.POST, Constant.SEND_EMAIL_FORGET, response -> {




            try {
                JSONObject object = new JSONObject(response);
                Toast.makeText(getApplicationContext(), ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                if (object.getBoolean("success"))
                {

                    sendEmailLayout.setVisibility(View.GONE);
                    resetPasswordLayout.setVisibility(View.VISIBLE);
                }

                Toast.makeText(getApplicationContext(), ""+object.getString("message"), Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        },error -> {
            error.printStackTrace();
            Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();


        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token","");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","bearer "+token);
                return map;
            }
            // add params

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
               // map.put("email",email.getText().toString().trim());
                map.put("email",forget_email_et.getText().toString().trim());
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }
}