package com.bdearning.group.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bdearning.group.Constant;

import com.bdearning.group.adapter.PackageAdapter;
import com.bdearning.group.model.PackageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bdearning.kormoappp.R;

public class PackageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView packageMessage;

    private SharedPreferences sharedPreferences;

    public static RecyclerView recyclerView;
    public static ArrayList<PackageModel> arrayList;

    private PackageAdapter packageAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pakage);

        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);


        toolbar = findViewById(R.id.toolbar_pakage);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("কর্ম জব");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        packageMessage = findViewById(R.id.package_message);
        packageMessage.setText(sharedPreferences.getString("pakage_message",""));

        progressBar = findViewById(R.id.progressBarPakageList);
        recyclerView = findViewById(R.id.recyclerPackageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        getPackageList();

    }

    private void getPackageList() {
        arrayList = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, Constant.PACKAGE_LIST, response -> {
            Log.e("121121","121");
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){

                    JSONArray array = new JSONArray(object.getString("data"));
                    for (int i = 0; i < array.length() ; i++) {


                        JSONObject postObject = array.getJSONObject(i);
                        Log.e("1211215555",postObject.getString("name")+"\t"+postObject.getString("fees"));
                        Log.e("mamber12121",postObject.getString("fees"));
                        Log.e("mamber12121", String.valueOf(postObject.getInt("id")));

                        PackageModel packageModel = new PackageModel();
                        packageModel.setName(postObject.getString("name"));
                        packageModel.setFees(postObject.getString("fees"));
                        packageModel.setEarnAmount(postObject.getString("earn_amount"));
                        packageModel.setStatus(postObject.getString("status"));
                        packageModel.setDescription(postObject.getString("description"));
                        packageModel.setId(postObject.getInt("id"));
                        int getId = postObject.getInt("id");
                        packageModel.setId(getId);

                        arrayList.add(packageModel);
                    }

                    packageAdapter = new PackageAdapter(getApplicationContext(),arrayList);
                    recyclerView.setAdapter(packageAdapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        },error -> {
            error.printStackTrace();
        }){

            // provide token in header

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token","");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","bearer "+token);
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