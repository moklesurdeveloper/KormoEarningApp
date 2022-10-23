package com.bdearning.group.adapter;

import android.content.Context;
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
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bdearning.kormoappp.R;


public class AdsWorkAdapter extends RecyclerView.Adapter<AdsWorkAdapter.PostsHolder> {

    private Context context;
    private ArrayList<PackageModel> list;
    SharedPreferences sharedPreferences,sharedP_id_fee_Send;

    public AdsWorkAdapter(Context context, ArrayList<PackageModel> list) {
        this.context = context;
        this.list = list;
          sharedPreferences  = context.getSharedPreferences("user", Context.MODE_PRIVATE);
          sharedP_id_fee_Send  = context.getSharedPreferences("id_fee_Send", Context.MODE_PRIVATE);


    }

    @NonNull
    @Override
    public PostsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_layout_desing,parent,false);
        return new PostsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsHolder holder, int position) {

         StartAppAd startAppAd = new StartAppAd(context);

        PackageModel packageModel = list.get(position);
        holder.packageName.setText(sharedP_id_fee_Send.getString("earn_amount",""));
        holder.video.setOnClickListener(v ->
        {



            startAppAd.showAd();
            startAppAd.loadAd (new AdEventListener() {

                @Override
                public void onReceiveAd(Ad ad)
                {

                }
                @Override
                public void onFailedToReceiveAd(Ad ad)
                {
                   // Toast.makeText(context, "k", Toast.LENGTH_SHORT).show();

                }
            });

            startAppAd.showAd(new AdDisplayListener() {
                @Override
                public void adHidden(Ad ad) {
                }
                @Override
                public void adDisplayed(Ad ad)
                {

                    amountAdd();
                    //Toast.makeText(context, "a", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void adClicked(Ad ad) {
                }
                @Override
                public void adNotDisplayed(Ad ad) {
                }
            });

        });


        packageInfo();



    }

    private void packageInfo() {

        StringRequest request = new StringRequest(Request.Method.GET, Constant.PACKAGE_INFO, response -> {


            try {
                JSONObject object = new JSONObject(response);

                if (object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("data");
                    Log.e("12112112",user.getString("total_work"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        },error -> {
            error.printStackTrace();
            Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
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
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    private void amountAdd() {

        int getPackageId = sharedP_id_fee_Send.getInt("position",0);
        String getFee = sharedP_id_fee_Send.getString("earn_amount","");



        StringRequest request = new StringRequest(Request.Method.POST, Constant.Tk_store, response -> {

            try {
                JSONObject object = new JSONObject(response);
                Log.e("123654",object.getString("message"));
                Toast.makeText(context, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                if (object.getBoolean("success")){
                    // update the post in recycler view

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        },error -> {
            error.printStackTrace();
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
                map.put("package_id",""+getPackageId);
                map.put("amount",getFee);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class PostsHolder extends RecyclerView.ViewHolder{

        private TextView packageInfoMessage,totalWork,packageName,video;


        public PostsHolder(@NonNull View itemView) {
            super(itemView);

            packageName = itemView.findViewById(R.id.textView10);
            video = itemView.findViewById(R.id.videoPlay);
         /*   packageInfoMessage = itemView.findViewById(R.id.package_info_message);
            packageUpgrade = itemView.findViewById(R.id.upgrade);*/

        }
    }


}
