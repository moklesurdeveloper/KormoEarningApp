package com.bdearning.group.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import bdearning.kormoappp.R;


public class OpenActivity extends AppCompatActivity {


    private SharedPreferences userPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);




        //this code will pause the app for 1.5 secs and then any thing in run method will run.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                editor = userPref.edit();
                boolean isLoggedIn = userPref.getBoolean("isLoggedIn",false);

       /*         if (userPref.getString("logOut","").equals("true"))
                {
                    Toast.makeText(getApplicationContext(), ""+userPref.getString("logOut",""), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }
                else
                {*/



                if (isLoggedIn){
                    // startActivity(new Intent(SplashActivity.this,CashOutActivity.class));
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    // startActivity(new Intent(SplashActivity.this, WorkStatusActivity.class));
                    finish();
                    editor.putBoolean("isLoggedIn",true);
                    editor.apply();
                }

                else {
                    startActivity(new Intent(getApplicationContext(),AuthActivity.class));
                    finish();
                }
            }


            /*  }*/
        },3000);
    }
}