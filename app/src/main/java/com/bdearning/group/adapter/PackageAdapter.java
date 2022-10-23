package com.bdearning.group.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bdearning.group.Constant;
import com.bdearning.group.model.PackageModel;
import com.bdearning.group.ui.AdsWorkActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bdearning.kormoappp.R;


public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PostsHolder> {

    private Context context;
    private ArrayList<PackageModel> list;

    SharedPreferences sharedPreferences,sharedP_id_fee_Send;
    SharedPreferences.Editor editor_id_fee_Send;

    public PackageAdapter(Context context, ArrayList<PackageModel> list) {
        this.context = context;
        this.list = list;
          sharedPreferences  = context.getSharedPreferences("user", Context.MODE_PRIVATE);;
          sharedP_id_fee_Send  = context.getSharedPreferences("id_fee_Send", Context.MODE_PRIVATE);
          editor_id_fee_Send = sharedP_id_fee_Send.edit();


    }

    @NonNull
    @Override
    public PostsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_desing,parent,false);
        return new PostsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsHolder holder, @SuppressLint("RecyclerView") int position) {

        Log.e("78965412",sharedPreferences.getString("package_one_name",""));
        Log.e("78965412",sharedPreferences.getString("package_one_fees",""));
        Log.e("78965412",sharedPreferences.getString("package_one_earn_amount",""));
        Log.e("78965412",sharedPreferences.getString("package_one_id",""));

        PackageModel memberShipBuy = list.get(position);
        holder.packageName.setText(memberShipBuy.getName());
        holder.packageInfoMessage.setText(memberShipBuy.getDescription());
        holder.package_fee.setText("Amount = "+memberShipBuy.getFees());

        if (position  == 0)
         {
             holder.packageUpgrade.setText("একটিভ");
         }
         if (sharedPreferences.getString("package_one_name","").equals(memberShipBuy.getName()))
         {
             holder.packageUpgrade.setText("একটিভ");
         }



        holder.packageUpgrade.setOnClickListener(v ->
        {
            int p = position+1;

            if (holder.packageUpgrade.getText().toString().equals("একটিভ"))
            {
                editor_id_fee_Send.putInt("position",p);
                editor_id_fee_Send.putString("earn_amount",""+memberShipBuy.getEarnAmount());
                editor_id_fee_Send.apply();

                Intent intent=new Intent(context, AdsWorkActivity.class);
                intent.putExtra("position",position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);



            }

            if (0<position)
            {
                StringRequest request = new StringRequest(Request.Method.POST, Constant.PACKAGE_BUY, response -> {

                    try {
                        JSONObject object = new JSONObject(response);

                        if (sharedPreferences.getString("package_one_name","").equals(memberShipBuy.getName()))
                        {
                            holder.packageUpgrade.setText("একটিভ");
                        }
                        else
                        {
                            Toast.makeText(context, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        if (object.getBoolean("success")){
                            holder.packageUpgrade.setText("কিছুক্ষণ অপেক্ষা করুন");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },error -> {
                    error.printStackTrace();
                    Log.e("2222",""+error.getMessage());
                }){
                    //add token to header

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        String token = sharedPreferences.getString("token","");
                        HashMap<String,String> map = new HashMap<>();
                        map.put("Authorization","bearer "+token);
                        return map;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> map = new HashMap<>();
                        map.put("package_one_id",""+memberShipBuy.getId());
                        Log.e("187985777",""+memberShipBuy.getId());
                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);
                buyPackageRequest();
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    class PostsHolder extends RecyclerView.ViewHolder{

        private TextView packageInfoMessage,totalWork,packageName,packageUpgrade,package_fee;


        public PostsHolder(@NonNull View itemView) {
            super(itemView);

            packageName = itemView.findViewById(R.id.package_name);
            packageInfoMessage = itemView.findViewById(R.id.package_info_message);
            packageUpgrade = itemView.findViewById(R.id.package_upgrade);
            package_fee = itemView.findViewById(R.id.package_fee);

        }
    }


    private void buyPackageRequest() {


    }


}
