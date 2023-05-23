package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText etRegEmail , etRegUserName , etRegPhone;
    EditText etRegPassword , etRegConfPass;
    TextView tvLoginHere;
    Button btnRegister;

    Spinner select_user_spinner;
    String user_type;

    FirebaseAuth mAuth;
    ProgressBar progressBar;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        select_user_spinner = findViewById(R.id.select_user_spinner);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        etRegConfPass = findViewById(R.id.etRegPass);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);
        etRegPhone = findViewById(R.id.etRegPhone);
        etRegUserName = findViewById(R.id.etRegUserName);

        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, ""+mAuth.getUid(), Toast.LENGTH_SHORT).show();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        // Set up the spinner adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.select_user_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_user_spinner.setAdapter(adapter);

        select_user_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user_type = adapterView.getItemAtPosition(i).toString();

//                Toast.makeText(RegistrationActivity.this, "User: "+user_type, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnRegister.setOnClickListener(view ->{
            //Register user
            inputData();
        });

        tvLoginHere.setOnClickListener(view ->{
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private String fullname, email , password , confirmPassword , phoneNumber;

    private void inputData() {
        fullname = etRegUserName.getText().toString().trim();
        email = etRegEmail.getText().toString();
        password = etRegPassword.getText().toString();
        confirmPassword = etRegConfPass.getText().toString().trim();
        phoneNumber = etRegPhone.getText().toString().trim();

        //validate data
        if(TextUtils.isEmpty(fullname)){
            Toast.makeText(this, "Enter Name...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this, "Enter Phone Number...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email address...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 5){
            Toast.makeText(this, "password must be at least 6 character long....", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(this, "Password doesn't match..", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!confirmPassword.equals(password)){
            Toast.makeText(this, "Confirm Pass And Password doesn't match", Toast.LENGTH_SHORT).show();
            return;
        }

//        Toast.makeText(this, ""+user_type, Toast.LENGTH_SHORT).show();

        createUser();
    }

    private void createUser(){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    saveFirebaseData();
                }else{
                    Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveFirebaseData() {
        progressDialog.setMessage("Saving Account Info...");
        progressDialog.show();

        String timestamp = ""+System.currentTimeMillis();

        //setup data to save
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", "" + mAuth.getUid());
        hashMap.put("email", "" + email);
        hashMap.put("name", "" + fullname);
        hashMap.put("phone", "" + phoneNumber);
        hashMap.put("password", "" + password);
        hashMap.put("timestamp",""+timestamp);
        hashMap.put("accountType",user_type);
        hashMap.put("online","true");

        Log.d("accountType",user_type);

//        Toast.makeText(this, ""+user_type, Toast.LENGTH_SHORT).show();

        //save to do
        FirebaseFirestore.getInstance().collection("User")
                .document(mAuth.getUid())
                .set(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        db update
                        progressDialog.dismiss();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Can't save...", Toast.LENGTH_SHORT).show();
                    }
                });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        Toast.makeText(this, ""+ref, Toast.LENGTH_SHORT).show();
        Log.d("ref:",""+mAuth.getUid());
        ref.child(mAuth.getUid()).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //db update
                        progressDialog.dismiss();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed updating db
                        progressDialog.dismiss();
//                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.d("e.message",e.getMessage());
                        Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

//        }
//    }

}