package com.bdearning.group.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.bdearning.group.Constant;

import com.bdearning.group.adapter.SliderAdapter;
import com.bdearning.group.model.SliderData;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import bdearning.kormoappp.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    private SliderView sliderView;
    private LinearLayout mobileRecharge, cashOut, moneyAdd, packageId, withdrawHistory,reviewHome;
    private ConstraintLayout balanceTransfer;
    private TextView total_refer,amount,appNameToolbar;
    private ImageView refer;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private ImageView referImage;
    Button vpncheck;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        /**
         * fsit
         * password mmmmmm
         * jks Location this project
         */

        toolbar = findViewById(R.id.toolbarHome);
        mDrawerLayout = findViewById(R.id.drawerHome);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);



        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        Log.e("145874878",""+sharedPreferences.getString("point",""));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_open_24);


        packageId = findViewById(R.id.packageId);
        mobileRecharge = findViewById(R.id.mobile_rechargeId);
        cashOut = findViewById(R.id.cashOutId);
        moneyAdd = findViewById(R.id.maney_addID);
        withdrawHistory = findViewById(R.id.withdrawHistory);
        refer = findViewById(R.id.referImage);
        balanceTransfer = findViewById(R.id.balanceTransferHome);
        referImage = findViewById(R.id.referImage);
        reviewHome = findViewById(R.id.reviewHome);

        appNameToolbar = findViewById(R.id.appnameToolber);
        total_refer = findViewById(R.id.total_refer);
        amount = findViewById(R.id.amount);

        total_refer.setText("Lo...");
        amount.setText("Lo...");

        if (sharedPreferences.getString("refer_image","").isEmpty() || sharedPreferences.getString("refer_image","") == null)
        {

        }
        else
        {
            Picasso.get().load(sharedPreferences.getString("refer_image","")).into(referImage);
        }



        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        appNameToolbar.setText("Bd Earning Group"+"\n"+currentDateandTime);


        // widthwor = findViewById(R.id.wirdthowId);

        findViewById(R.id.notis).setOnClickListener(v ->
        {
            startActivity(new Intent(getApplicationContext(), NotisActivity.class));
        });



        mobileRecharge.setOnClickListener(this::onClick);
        cashOut.setOnClickListener(this::onClick);
        moneyAdd.setOnClickListener(this::onClick);
        packageId.setOnClickListener(this::onClick);
        withdrawHistory.setOnClickListener(this::onClick);
        refer.setOnClickListener(this::onClick);
        balanceTransfer.setOnClickListener(this::onClick);
        reviewHome.setOnClickListener(this::onClick);


        Imageslider();
        getImageFormAPI();
        getAllMessageNoticeAPI();
        getUserInfo();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_Communication:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_fb:
                        fb_url_open();
                        break;
                    case R.id.nav_Youtube:
                        youtube_url_open();
                        break;
                    case R.id.nav_WebSite:
                        web_url_open();
                        break;
                    case R.id.nav_rate_us:
                        rate_us_url_open();
                        break;
                    case R.id.nav_share_app:
                        share_url_open();
                        break;
                    case R.id.nav_dashboard:
                        open_deshBoard();
                        break;
                    case R.id.nav_log_out:
                        logOut();
                        break;
                }

                return true;
            }
        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.packageId:
                goToPackageCheckVpn();
                break;
            case R.id.mobile_rechargeId:
                startActivity(new Intent(getApplicationContext(), MobileRechargeActivity.class));
                finish();
                break;
            case R.id.cashOutId:
                startActivity(new Intent(getApplicationContext(), CashOutActivity.class));
                finish();
                break;
            case R.id.maney_addID:
                startActivity(new Intent(getApplicationContext(), ManeyAddActivity.class));
                finish();
                break;
            case R.id.withdrawHistory:
                startActivity(new Intent(getApplicationContext(), WithdrawHistoryActivity.class));
                finish();
                break;
            case R.id.referImage:
                startActivity(new Intent(getApplicationContext(), ReferActivity.class));
                finish();
                break;
            case R.id.balanceTransferHome:
                startActivity(new Intent(getApplicationContext(), BalanceTransferActivity.class));
                finish();
                break;
            case R.id.reviewHome:
                rate_us_url_open();
                break;

        }
    }

    private void checkVpn()
    {


        if (checkvpn())
        {
            startActivity(new Intent(getApplicationContext(), PackageActivity.class));
            finish();
        }
        else{
            AlertDialog.Builder mBuilder=new AlertDialog.Builder(HomeActivity.this);
            View mView=getLayoutInflater().inflate(R.layout.vpnblocker,null);
            vpncheck=mView.findViewById(R.id.vpnid);
            mBuilder.setView(mView);
            AlertDialog dialog=mBuilder.create();
            dialog.setCancelable(false);
            dialog.show();
            dialog.setCancelable(true);

            vpncheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                }
            });
        }
    }

    public boolean checkvpn(){
        List<String> networkList = new ArrayList<>();
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    networkList.add(networkInterface.getName());
            }
        } catch (Exception ex) {
            //Timber.d("isVpnUsing Network List didn't received");
        }

        return networkList.contains("tun0");
    }


    private void getUserInfo()
    {

        StringRequest request = new StringRequest(Request.Method.GET, Constant.USER_INFO, response -> {
            Log.e("121121","121");
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){

                    JSONObject user = object.getJSONObject("data");
                    Log.e("121121",user.getString("amount"));

                    editor.putString("name",user.getString("name"));
                    editor.putString("email",user.getString("email"));
                    editor.putString("own_refer_id",user.getString("own_refer_id"));
                    editor.putString("status",user.getString("status"));
                    editor.putString("amount",user.getString("amount"));
                    editor.putString("package_id",user.getString("package_id"));
                    editor.putString("package_name",user.getString("package_name"));
                    editor.putString("total_refer",user.getString("total_refer"));
                    editor.putString("user_id",user.getString("user_id"));
                    editor.putString("package_one_name",user.getString("package_one_name"));
                    editor.putString("package_one_fees",user.getString("package_one_fees"));
                    editor.putString("package_one_earn_amount",user.getString("package_one_earn_amount"));
                    editor.putString("package_one_id",user.getString("package_one_id"));
                    editor.putInt("pointR",1);
                    editor.apply();


                    amount.setText(user.getString("amount"));
                    total_refer.setText(user.getString("total_refer"));


                    Log.e("1415411","114");
                    Log.e("1415411",user.getString("package_one_name"));
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


    private void getAllMessageNoticeAPI()
    {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.MESSAGE, response -> {

            try {
                JSONObject object = new JSONObject(response);
                JSONObject getImageObject = object.getJSONObject("data");

                if (object.getBoolean("success")) {


                    editor.putString("admin_notice",getImageObject.getString("admin_notice"));
                    editor.putString("pakage_message",getImageObject.getString("pakage_message"));
                    editor.putString("recharge_message",getImageObject.getString("recharge_message"));
                    editor.putString("cash_out_message",getImageObject.getString("cash_out_message"));
                    editor.putString("add_money_message",getImageObject.getString("add_money_message"));
                    editor.putString("payment_number_message",getImageObject.getString("payment_number_message"));
                    editor.putString("add_notice",getImageObject.getString("add_notice"));
                    editor.apply();



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        }) {
            //add token to headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "bearer " + token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void getImageFormAPI()
    {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.IMAGE, response -> {

            try {
                JSONObject object = new JSONObject(response);
                JSONObject getImageObject = object.getJSONObject("data");


                editor.putString("slider",getImageObject.getString("image_one"));
                editor.putString("slider2",getImageObject.getString("image_two"));
                editor.putString("slider3",getImageObject.getString("image_three"));
                editor.putString("slider4",getImageObject.getString("image_four"));
                editor.putString("slider5",getImageObject.getString("image_five"));
                editor.putString("slider6",getImageObject.getString("image_six"));
                editor.putString("slider7",getImageObject.getString("image_seven"));
                editor.putString("slider8",getImageObject.getString("image_eight"));
                editor.putString("slider9",getImageObject.getString("image_nine"));
                editor.putString("slider10",getImageObject.getString("image_ten"));
                editor.putString("refer_image",getImageObject.getString("refer_image"));

                editor.putString("sliderUrl1",getImageObject.getString("image_one_url"));
                editor.putString("sliderUrl2",getImageObject.getString("image_two_url"));
                editor.putString("sliderUrl3",getImageObject.getString("image_three_url"));
                editor.putString("sliderUrl4",getImageObject.getString("image_four_url"));
                editor.putString("sliderUrl5",getImageObject.getString("image_five_url"));
                editor.putString("sliderUrl6",getImageObject.getString("image_six_url"));
                editor.putString("sliderUrl7",getImageObject.getString("image_seven_url"));
                editor.putString("sliderUrl8",getImageObject.getString("image_eight_url"));
                editor.putString("sliderUrl9",getImageObject.getString("image_nine_url"));
                editor.putString("sliderUrl10",getImageObject.getString("image_ten_url"));
                editor.apply();

                Log.e("14785899",getImageObject.getString("image_one_url"));
                Log.e("14785899",getImageObject.getString("image_ten_url"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        }) {
            //add token to headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "bearer " + token);
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void open_deshBoard() {
        startActivity(new Intent(getApplicationContext(), DeshBoardActivity.class));
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }


    private void Imageslider() {
        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        // initializing the slider view.
        SliderView sliderView = findViewById(R.id.slider);
        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(sharedPreferences.getString("slider","")));
        sliderDataArrayList.add(new SliderData(sharedPreferences.getString("slider2","")));
        sliderDataArrayList.add(new SliderData(sharedPreferences.getString("slider3","")));
        sliderDataArrayList.add(new SliderData(sharedPreferences.getString("slider4","")));
        sliderDataArrayList.add(new SliderData(sharedPreferences.getString("slider5","")));


        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }

    private void logOut()
    {
        StringRequest request = new StringRequest(Request.Method.POST, Constant.LOGOUT, response ->
        {

            editor.putBoolean("isLoggedIn",false);
            editor.apply();

            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
            getApplicationContext();
            finish();

        }, error -> {
            error.printStackTrace();
        }) {

            // provide token in header

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "bearer " + token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void share_url_open() {

    }

    private void rate_us_url_open()
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));

    }

    private void web_url_open() {
        Uri uri = Uri.parse("https://www.cadstore.xyz/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void youtube_url_open() {
        Uri uri = Uri.parse("https://www.youtube.com/channel/UCuShZ5us-rTUJxCOkw3jcVQ"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void fb_url_open() {
        Uri uri = Uri.parse("https://www.facebook.com/CAD.Chittagong/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }




    private void goToPackageCheckVpn()
    {
        checkVpn();


    }


}