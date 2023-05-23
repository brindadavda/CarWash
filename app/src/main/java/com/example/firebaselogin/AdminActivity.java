package com.example.firebaselogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class AdminActivity extends AppCompatActivity {

    private Button btnOwner ,btnEmployee , btnCustomer;
    private ImageView logoutIv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //
        init();

        logoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, LogoutActivity.class));
            }
        });



        btnOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,OwnerActivity.class);
                i.putExtra("Sender","Admin");
                startActivity(i);
            }
        });

        btnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,EmployeeActivity.class);
                i.putExtra("Sender","Admin");
                startActivity(i);
            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,CustomerActivity.class);
                i.putExtra("Sender","Admin");
                startActivity(i);
            }
        });
    }

    void init(){
        btnOwner = findViewById(R.id.btnOwner);
        btnEmployee = findViewById(R.id.btnEmployee);
        btnCustomer = findViewById(R.id.btnCustomer);
        logoutIv = findViewById(R.id.logoutIv);
    }
}