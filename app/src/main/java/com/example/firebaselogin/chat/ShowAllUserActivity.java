package com.example.firebaselogin.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.firebaselogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllUserActivity extends AppCompatActivity {

    private RecyclerView rv;

    List<ModelShowUser> list;


    String accountType , senderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_user);

        rv = findViewById(R.id.rv);

        //get account type form intent
        Intent i = getIntent();
        accountType = i.getStringExtra("accountType");
        senderId = i.getStringExtra("senderID");


        list = new ArrayList<>();

        //get data based on accountType
        getData();




    }

    private void setData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        Toast.makeText(ShowAllUserActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();

        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);

        AdapterShowUser adapterShowUser = new AdapterShowUser(this,list);

        rv.setAdapter(adapterShowUser);
    }

    private void getData() {

        //try to get data
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Toast.makeText(ShowAllUserActivity.this, ""+document.getData(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", document.getId() + " => " + document.getData());

                        String aType = document.getData().get("accountType").toString();
                        String name = "" , phone = "" , id = "" ;

                        if(aType.equals(accountType)) {
                             name = document.getData().get("name").toString();
                             phone = document.getData().get("phone").toString();
                             id = document.getId();

                            ModelShowUser o =  new ModelShowUser(senderId,id,name,phone);
                            list.add(o);
//                            Toast.makeText(ShowAllUserActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();
                        }

                        Log.d("TAG1", "name: "+name+", phone: "+phone);
                    }

                    //now set data to rv
                    setData();

                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}