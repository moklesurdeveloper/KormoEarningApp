package com.bdearning.group.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import bdearning.kormoappp.R;

public class DeshBoardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView name,email,refer,amount,totalCashAmount,freePackageName,buyPackageName_dashBoard,copy;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desh_board);

        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        toolbar = findViewById(R.id.toolbarDeshBoard);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle(getString(R.string.deshborad));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = findViewById(R.id.name_dashBoard);
        email = findViewById(R.id.email_dashBoard);
        refer = findViewById(R.id.refer_dashBoard);
        amount = findViewById(R.id.amount_dashBoard);
        freePackageName = findViewById(R.id.free_packageName_dashBoard);
        buyPackageName_dashBoard = findViewById(R.id.buyPackageName_dashBoard);
        totalCashAmount = findViewById(R.id.total_cashOut_dashBoard);
        copy = findViewById(R.id.copy_dashBorad);

        name.setText(sharedPreferences.getString("name",""));
        email.setText(sharedPreferences.getString("email",""));
        refer.setText(sharedPreferences.getString("own_refer_id",""));
        amount.setText("Total Balance "+sharedPreferences.getString("amount",""));
        freePackageName.setText(sharedPreferences.getString("package_name","")+"\tPackage");
        buyPackageName_dashBoard.setText(sharedPreferences.getString("package_one_name","")+" Package");
        totalCashAmount.setText("Total Cash Out Balance "+sharedPreferences.getString("totalCashOutAmount",""));

        /**
         * Buy package check
         */
        if (sharedPreferences.getString("package_one_name","").equals("null"))
        {
            buyPackageName_dashBoard.setText("Not Buy Package");
        }


        copy.setOnClickListener(v ->
        {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copy", refer.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(getApplicationContext(), "Copy Refer Code "+refer.getText().toString(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() ==android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}