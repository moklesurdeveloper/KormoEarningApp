package com.bdearning.group.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import bdearning.kormoappp.R;

public class ReferActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView referImage;
    private TextView refer,copyTextButton;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);


        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        toolbar = findViewById(R.id.toolbarefer);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.refer_txt));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        referImage = findViewById(R.id.referImg);
        refer = findViewById(R.id.refer);
        copyTextButton = findViewById(R.id.copyTextButton);
        refer.setText("রেফার কোড - "+sharedPreferences.getString("own_refer_id",""));

        copyTextButton.setOnClickListener(v ->
        {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copy", refer.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(getApplicationContext(), refer.getText().toString(), Toast.LENGTH_SHORT).show();
        });


        Picasso.get().load(sharedPreferences.getString("refer_image","")).into(referImage);

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