package com.bdearning.group.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import bdearning.kormoappp.R;


public class NotisActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView adminNotice;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notis);

        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        toolbar = findViewById(R.id.toolbarNotice);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.admin_notices));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        adminNotice = findViewById(R.id.noticeAdmin);
        adminNotice.setText(sharedPreferences.getString("admin_notice",""));


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}