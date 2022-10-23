package com.bdearning.group.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bdearning.group.adapter.AdsWorkAdapter;
import com.bdearning.group.model.PackageModel;
import com.squareup.picasso.Picasso;
import com.startapp.sdk.ads.interstitials.InterstitialAd;

import java.util.ArrayList;

import bdearning.kormoappp.R;

public class AdsWorkActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    public static RecyclerView recyclerView;
    public static ArrayList<PackageModel> arrayList;
    private AdsWorkAdapter adsWorkAdapter;
    private TextView add_view_message;
    private InterstitialAd interstitial;
  //  private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        arrayList = new ArrayList<>();

        toolbar = findViewById(R.id.toolbarWork);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.daily_work));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        alertDialogShow();

        add_view_message = findViewById(R.id.add_view_message);
        add_view_message.setText(sharedPreferences.getString("add_notice",""));

        //    progressBar = findViewById(R.id.progressBarPakageList);
        recyclerView = findViewById(R.id.recyclerViewWork);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        for (int i = 0; i <10; i++) {

            PackageModel model = new PackageModel();
            model.setName("0.2");
            arrayList.add(model);
        }

        adsWorkAdapter = new AdsWorkAdapter(getApplicationContext(),arrayList);
        recyclerView.setAdapter(adsWorkAdapter);






    }

    private void alertDialogShow()
    {

        int getPackagePosition = getIntent().getExtras().getInt("position");

        // custom dialog
        final Dialog dialog = new Dialog(AdsWorkActivity.this);
        dialog.setContentView(R.layout.custom_alertdialog);
        ImageButton dialogButton = dialog.findViewById(R.id.closeImage);
        ImageView setImage = dialog.findViewById(R.id.dailgoImage);



        if (getPackagePosition ==0)
        {
            Picasso.get().load(Uri.parse(sharedPreferences.getString("slider6",""))).into(setImage);
        }
        else if (getPackagePosition ==1)
        {
            Picasso.get().load(Uri.parse(sharedPreferences.getString("slider7",""))).into(setImage);
        }
        else if (getPackagePosition ==2)
        {
            Picasso.get().load(Uri.parse(sharedPreferences.getString("slider8",""))).into(setImage);
        }
        else if (getPackagePosition ==3)
        {
            Picasso.get().load(Uri.parse(sharedPreferences.getString("slider9",""))).into(setImage);
        }
        else if (getPackagePosition ==4)
        {
            Picasso.get().load(Uri.parse(sharedPreferences.getString("slider10",""))).into(setImage);
        }


        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               // Toast.makeText(getApplicationContext(),"Close..!!",Toast.LENGTH_SHORT).show();
            }
        });

        setImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


                if (getPackagePosition ==0)
                {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(sharedPreferences.getString("sliderUrl6","")));
                    startActivity(i);
                }
                else if (getPackagePosition ==1)
                {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(sharedPreferences.getString("sliderUrl7","")));
                    startActivity(i);
                }
                else if (getPackagePosition ==2)
                {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(sharedPreferences.getString("sliderUrl8","")));
                    startActivity(i);
                }
                else if (getPackagePosition ==3)
                {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(sharedPreferences.getString("sliderUrl9","")));
                    startActivity(i);
                }
                else if (getPackagePosition ==4)
                {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(sharedPreferences.getString("sliderUrl10","")));
                    startActivity(i);
                }

            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("id_fee_Send", Context.MODE_PRIVATE);
        settings.edit().clear().commit();

        super.onBackPressed();
    }
}