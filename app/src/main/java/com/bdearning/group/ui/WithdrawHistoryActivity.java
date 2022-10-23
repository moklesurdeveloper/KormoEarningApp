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
import com.bdearning.group.adapter.WithdrawHistoryAdapter;
import com.bdearning.group.model.WithdrawHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bdearning.kormoappp.R;

public class WithdrawHistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public static RecyclerView recyclerView;
   public static ArrayList<WithdrawHistoryModel> arrayList;
    private WithdrawHistoryAdapter withdrawHistoryAdapter;
    private TextView total_cashOutAmount,total_transferAmount;

    private PackageAdapter packageAdapter;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrow_histroy);

        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);


        toolbar = findViewById(R.id.toolbarWithdrawHistory);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.withdraw_txt));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        total_cashOutAmount = findViewById(R.id.total_cashOutAmount);
        total_transferAmount = findViewById(R.id.total_transferAmount);
        progressBar = findViewById(R.id.progressBarHistory);
        recyclerView = findViewById(R.id.recyer_withdrawHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        total_cashOutAmount.setText("Loading...");
        total_transferAmount.setText("Loading...");

       getWithdrawHistory();
        total_cashOutAmount();
    }




   private void getWithdrawHistory() {
        arrayList = new ArrayList<>();
       Log.e("121121","121");
        StringRequest request = new StringRequest(Request.Method.GET, Constant.WITHDRAW_LIST, response -> {


            progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){

                    JSONArray array = new JSONArray(object.getString("data"));

                    for (int i = 0; i < array.length() ; i++) {

                        JSONObject postObject = array.getJSONObject(i);

                        WithdrawHistoryModel model = new WithdrawHistoryModel();
                        model.setAmount(postObject.getString("amount"));
                        model.setMethodName(postObject.getString("method_name"));
                        model.setStatus(postObject.getString("status"));

                        Log.e("125485",postObject.getString("status"));

                        arrayList.add(model);
                    }

                    withdrawHistoryAdapter = new WithdrawHistoryAdapter(getApplicationContext(),arrayList);
                    recyclerView.setAdapter(withdrawHistoryAdapter);
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


    private void total_cashOutAmount()
    {

        StringRequest request = new StringRequest(Request.Method.GET, Constant.USER_INFO, response -> {
            Log.e("121121","121");
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){

                    JSONObject user = object.getJSONObject("data");
                    Log.e("121121",user.getString("amount"));


                    total_cashOutAmount.setText("Total withdraw amount = "+user.getString("total_withdraw"));
                    total_transferAmount.setText("Total transfer amount = "+user.getString("total_transfer"));

                    Log.e("1415411",user.getString("total_withdraw"));
                    //   pId.equals("1") || pId.equals("2") || pId.equals("3") || pId.equals("4")


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