package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;
    Spinner select_user_spinner;
    String user_type;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPass);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        btnLogin = findViewById(R.id.btnLogin);
        select_user_spinner = findViewById(R.id.select_user_spinner);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view ->{
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });


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


    }

    private void loginUser(){
        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();


        if (TextUtils.isEmpty(email)){
            etLoginEmail.setError("Email cannot be empty");
            etLoginEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            etLoginPassword.setError("Password cannot be empty");
            etLoginPassword.requestFocus();
        } else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        getUserType();
                    }else{
                        Toast.makeText(LoginActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void getUserType() {
        //try to get data
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("User").document(mAuth.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    if(task.getResult().get("accountType").equals(user_type)){
                        Intent i ;
                            switch (user_type){
                                case "Admin":
                                    Toast.makeText(LoginActivity.this, "Admin", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this,AdminActivity.class));
                                    finish();
                                    break;
                                case "Customer":
                                    Toast.makeText(LoginActivity.this, "Customer", Toast.LENGTH_SHORT).show();
                                    i = new Intent(LoginActivity.this,CustomerActivity.class);
                                    i.putExtra("senderID",mAuth.getUid());
                                    i.putExtra("Sender","Customer");
                                    startActivity(i);
                                    break;
                                case "Employee":
                                    i = new Intent(LoginActivity.this,EmployeeActivity.class);
                                    i.putExtra("senderID",mAuth.getUid());
                                    i.putExtra("Sender","Employee");
                                    startActivity(i);
                                    Toast.makeText(LoginActivity.this, "Employee", Toast.LENGTH_SHORT).show();
                                    finish();
                                    break;
                                case "Owner":
                                    i = new Intent(LoginActivity.this,OwnerActivity.class);
                                    i.putExtra("senderID",mAuth.getUid());
                                    i.putExtra("Sender","Owner");
                                    startActivity(i);
                                    Toast.makeText(LoginActivity.this, "Owner", Toast.LENGTH_SHORT).show();
                                    finish();
                                    break;

                            }
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Please Select Proper role...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}