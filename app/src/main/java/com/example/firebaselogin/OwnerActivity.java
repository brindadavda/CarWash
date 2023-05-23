package com.example.firebaselogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

public class OwnerActivity extends AppCompatActivity {

    private EditText mOfferInput;
    private EditText mEmployeeInput;
    private EditText mRateInput;
    private Spinner mWashTypeSpinner;
    private Button mAddOfferButton;
    private Button mAddEmployeeButton;
    private Button mSetRateButton;
    private ImageView logoutIv;
    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        // Get references to the input views
        init();

        logoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnerActivity.this,LogoutActivity.class));
            }
        });

        // Set up the spinner adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.wash_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWashTypeSpinner.setAdapter(adapter);

        // Get references to the buttons and attach click listeners to them
        mAddOfferButton = findViewById(R.id.add_offer_button);
        mAddOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the offer input from the view
                String offer = mOfferInput.getText().toString();

                if(TextUtils.isEmpty(offer)){
                    Toast.makeText(OwnerActivity.this, "Please add offer..", Toast.LENGTH_SHORT).show();
                    return;
                }

                str += offer+",";

                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });

        mAddEmployeeButton = findViewById(R.id.add_employee_button);
        mAddEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the employee input from the view
                String employee = mEmployeeInput.getText().toString();

                if(TextUtils.isEmpty(employee)){
                    Toast.makeText(OwnerActivity.this, "Please add employee name..", Toast.LENGTH_SHORT).show();
                    return;
                }

                str += employee+",";

                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });

        mSetRateButton = findViewById(R.id.set_rate_button);
        mSetRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the rate and wash type inputs from the views

                if(TextUtils.isEmpty(mRateInput.getText().toString())){
                    Toast.makeText(OwnerActivity.this, "Please add rate..", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(mWashTypeSpinner.getSelectedItem().toString())){
                    Toast.makeText(OwnerActivity.this, "Please select wash type..", Toast.LENGTH_SHORT).show();
                    return;
                }

                float rate = Float.parseFloat(mRateInput.getText().toString());
                String washType = mWashTypeSpinner.getSelectedItem().toString();

                str += washType +" : "+rate+".";

                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });


    }

    void init(){
        mOfferInput = findViewById(R.id.offer_input);
        mEmployeeInput = findViewById(R.id.employee_input);
        mRateInput = findViewById(R.id.rate_input);
        mWashTypeSpinner = findViewById(R.id.wash_type_spinner);
        logoutIv = findViewById(R.id.logoutIv);
    }
}