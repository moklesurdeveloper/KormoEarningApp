package com.bdearning.group.ui.Fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bdearning.kormoappp.R;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class SignUpFragment extends Fragment {
    private View view;
    private TextInputLayout layoutEmail,layoutPassword,layoutConfirm;
    private TextInputEditText txtName,txtEmail,txtPassword,txtConfirm,txtReferSignUp;
    private TextView txtSignIn;
    private TextView btnSignUp;
    private ProgressDialog dialog;

    public SignUpFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_sign_up,container,false);
        init();
        return view;


    }

    private void init() {

        layoutPassword = view.findViewById(R.id.txtLayoutPasswordSignUp);
        layoutEmail = view.findViewById(R.id.txtLayoutEmailSignUp);
        layoutConfirm = view.findViewById(R.id.txtLayoutConfrimSignUp);
        txtName = view.findViewById(R.id.txtNameSignUp);
        txtPassword = view.findViewById(R.id.txtPasswordSignUp);
        txtConfirm = view.findViewById(R.id.txtConfirmSignUp);
        txtSignIn = view.findViewById(R.id.txtSignIn);
        txtEmail = view.findViewById(R.id.txtEmailSignUp);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        txtReferSignUp = view.findViewById(R.id.txtReferSignUp);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        txtSignIn.setOnClickListener(v->{
            //change fragments
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SignInFragment()).commit();
        });

        btnSignUp.setOnClickListener(v->{
            //validate fields first
            if (validate()){
               register();
            }
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

        txtConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtConfirm.getText().toString().equals(txtPassword.getText().toString())){
                    layoutConfirm.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        if (!txtConfirm.getText().toString().equals(txtPassword.getText().toString())){
            layoutConfirm.setErrorEnabled(true);
            layoutConfirm.setError("Password does not match");
            return false;
        }


        return true;
    }


    private void register(){

       // dialog.setMessage("Registering");
       // dialog.show();

                ACProgressFlower dialog = new ACProgressFlower.Builder(getContext())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Registering")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Constant.REGISTER, response -> {
            //we get response if connection success


            try {
                JSONObject object = new JSONObject(response);
                Toast.makeText(getContext(), ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                try {

                    if (object.getBoolean("success"))
                    {
                      /*  startActivity(new Intent(getContext(), LoginActivity.class));
                        finish();*/
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SignInFragment()).commit();

                        JSONObject user = object.getJSONObject("data");
                        //make shared preference user
                        SharedPreferences userPref = getContext().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = userPref.edit();
                        editor.putString("token",object.getString("token"));
                        editor.putString("name",user.getString("name"));
                        editor.putString("email",user.getString("email"));
                        editor.putInt("id",user.getInt("id"));
                        editor.putBoolean("isLoggedIn",true);
                        editor.apply();
                        //if success

                        Log.e("141414",""+user.getInt("id"));
                        Toast.makeText(getContext(), ""+user.getInt("id"), Toast.LENGTH_SHORT).show();

                    }
                }catch
                (Exception e) {
                }
                dialog.dismiss();


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();

        },error -> {
            // error if connection not success
            error.printStackTrace();
            dialog.dismiss();
            Log.e("llll",""+error.getMessage());
            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();

        }){

            // add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();

                map.put("name",txtName.getText().toString());
                map.put("email",txtEmail.getText().toString());
                map.put("password",txtPassword.getText().toString());
                map.put("refer_id",txtReferSignUp.getText().toString());
                map.put("country","");
                return map;
            }


        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }


}
