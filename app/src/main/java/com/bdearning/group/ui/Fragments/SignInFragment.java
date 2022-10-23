package com.bdearning.group.ui.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.bdearning.group.Constant;
import com.bdearning.group.ui.AuthActivity;
import com.bdearning.group.ui.ForgetActivity;
import com.bdearning.group.ui.HomeActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bdearning.kormoappp.R;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class SignInFragment extends Fragment {
    private View view;
    private TextInputLayout layoutEmail,layoutPassword;
    private TextInputEditText txtEmail,txtPassword;
    private TextView txtSignUp;
    private TextView btnSignIn,btnForget;
    private ProgressDialog dialog;

    public SignInFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_sign_in,container,false);
        init();
        return view;
    }

    private void init() {
        layoutPassword = view.findViewById(R.id.txtLayoutPasswordSignIn);
        layoutEmail = view.findViewById(R.id.txtLayoutEmailSignIn);
        txtPassword = view.findViewById(R.id.txtPasswordSignIn);
        txtSignUp = view.findViewById(R.id.txtSignUp);
        txtEmail = view.findViewById(R.id.txtEmailSignIn);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnForget = view.findViewById(R.id.btnForget);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        txtSignUp.setOnClickListener(v->{
            //change fragments
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SignUpFragment()).commit();
        });

        btnSignIn.setOnClickListener(v->{
            //validate fields first
            if (validate()){
              login();
            }
        });

        btnForget.setOnClickListener(v->{
            //validate fields first
           // forget();
        });


        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtEmail.getText().toString().isEmpty()){
                    layoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtPassword.getText().toString().length()>7){
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void forget()
    {

        startActivity(new Intent(getContext(), ForgetActivity.class));
    }


    private boolean validate (){


        if (txtEmail.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is Required");
            return false;
        }
        if (txtPassword.getText().toString().length()<8){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 8 characters");
            return false;
        }
        return true;
    }


    private void login (){
   /*     dialog.setMessage("Logging in");
        dialog.show();*/
        ACProgressFlower dialog = new ACProgressFlower.Builder(getContext())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Logging in")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, Constant.LOGIN, response -> {
            //we get response if connection success

            try {
                JSONObject object = new JSONObject(response);

            //    Toast.makeText(getContext(), ""+object.getString("message"), Toast.LENGTH_SHORT).show();


                if (object.getBoolean("success")){
                    Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();

                    SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token",object.getString("token"));
                    editor.putBoolean("isLoggedIn",true);
                    editor.apply();
                    //if success
                    //   startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    startActivity(new Intent(getContext(), HomeActivity.class));
                    ((AuthActivity) getContext()).finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        },error -> {
            // error if connection not success
            error.printStackTrace();
            dialog.dismiss();
            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
        }){

            // add parameters


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("email",txtEmail.getText().toString().trim());
                map.put("password",txtPassword.getText().toString());
                return map;
            }
        };

        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }














}
