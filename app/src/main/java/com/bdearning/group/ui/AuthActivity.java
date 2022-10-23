package com.bdearning.group.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bdearning.group.ui.Fragments.SignInFragment;

import bdearning.kormoappp.R;


public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SignInFragment()).commit();

    }
}