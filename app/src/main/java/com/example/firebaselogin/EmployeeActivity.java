package com.example.firebaselogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.firebaselogin.chat.ChatActivity;
import com.example.firebaselogin.chat.ShowAllUserActivity;

public class EmployeeActivity extends AppCompatActivity {

    String senderID;
    private ImageView logoutIv;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button chatCustomerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        // Retrieve the selected date and time from the pickers
        init();

        logoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeActivity.this,LogoutActivity.class));
            }
        });

        Intent i = getIntent();
        senderID = i.getStringExtra("senderID");
        String sender = i.getStringExtra("Sender");

        if(sender.equals("Admin")){
            chatCustomerBtn.setVisibility(View.GONE);
        }
    }

    public void confirmAvailability(View view) {

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int dayOfMonth = datePicker.getDayOfMonth();

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String str = "Date : " + dayOfMonth + "/"+month+"/"+year;
        str += ",Time : "+hour+":"+minute;

        // Use the selected date and time to schedule the service
        // ...

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void chatCustomer(View v){
        Intent i = new Intent(EmployeeActivity.this, ShowAllUserActivity.class);
        i.putExtra("accountType","Customer");
        i.putExtra("senderID",senderID);
        startActivity(i);
    }

    private void init(){
        datePicker = findViewById(R.id.date_picker);
        timePicker = findViewById(R.id.time_picker);
        logoutIv = findViewById(R.id.logoutIv);
        chatCustomerBtn = findViewById(R.id.chatCustomerBtn);
    }

}