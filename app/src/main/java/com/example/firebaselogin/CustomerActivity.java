package com.example.firebaselogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.firebaselogin.chat.ChatActivity;
import com.example.firebaselogin.chat.ShowAllUserActivity;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerActivity extends AppCompatActivity {

    private RadioGroup availabilityRadioGroup;
    private Button chatOwnerBtn, chatEmployeeBtn , bookButton;
    private ImageView logoutIv;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        // Initialize UI components
        init();

        Intent i = getIntent();
        String senderId = i.getStringExtra("senderID");
        String sender = i.getStringExtra("Sender");

        if(sender.equals("Admin")){
            chatEmployeeBtn.setVisibility(View.GONE);
            chatOwnerBtn.setVisibility(View.GONE);
        }

        logoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerActivity.this, LogoutActivity.class));
            }
        });


        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Perform booking logic here
                int selectedRadioButtonId = availabilityRadioGroup.getCheckedRadioButtonId();

                if(TextUtils.isEmpty(String.valueOf(selectedRadioButtonId))){
                    Toast.makeText(CustomerActivity.this, "Select time please", Toast.LENGTH_SHORT).show();
                    return;
                }


                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

                String selectedTime = selectedRadioButton.getText().toString();
                Toast.makeText(CustomerActivity.this, "Booking successful for " + selectedTime, Toast.LENGTH_SHORT).show();
            }
        });

        chatEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Customer can chat with Employee
                Toast.makeText(CustomerActivity.this, "Chat With Employee", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), ShowAllUserActivity.class);
                i.putExtra("accountType","Employee");
                i.putExtra("senderID",senderId);
                startActivity(i);
            }
        });

        chatOwnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Customer can chat with Owner
                Toast.makeText(CustomerActivity.this, "Chat With Owner", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), ShowAllUserActivity.class);
                i.putExtra("accountType","Owner");
                i.putExtra("senderID",senderId);
                startActivity(i);
            }
        });
    }

    void init(){
        availabilityRadioGroup = findViewById(R.id.availability_radiogroup);
        bookButton = findViewById(R.id.book_button);
        chatEmployeeBtn = findViewById(R.id.chatEmployeeBtn);
        chatOwnerBtn = findViewById(R.id.chatOwnerBtn);
        logoutIv = findViewById(R.id.logoutIv);
    }
}