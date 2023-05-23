package com.example.firebaselogin.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.firebaselogin.LoginActivity;
import com.example.firebaselogin.RegisterActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChatService {

    private Context context;
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    CollectionReference db = FirebaseFirestore.getInstance().collection("Chat");

    private String receiveID , senderID;

    public ChatService(Context context, String receiveID , String senderID) {
        this.context = context;
        this.receiveID = receiveID;
        this.senderID = senderID;
    }

    public ChatService(Context context) {
        this.context = context;
    }

    public void readChatData(final OnReadChatCallBack onCallBack){
        reference.child("Chats").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Chats> list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                        Log.d("TAG",""+snapshot1.getValue(Chats.class));
//                        Toast.makeText(ChatsActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();
                    Chats chats = snapshot1.getValue(Chats.class);
//                        Toast.makeText(ChatsActivity.this,  "Reciver: "+chats.getReceiver() + " "+ReceiverID, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(ChatsActivity.this, "Sender: "+chats.getSender() + " "+firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                    if ((chats.getSender().equals(senderID)) && (chats.getReceiver().equals(receiveID)) || ((chats.getSender().equals(receiveID)) && (chats.getReceiver().equals(firebaseUser.getUid())))) {
                        list.add(chats);
                    }
                }
                onCallBack.onReadSuccess(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onCallBack.onReadFailed();
            }
        });
    }

    public void sendTextMsg(String text){
//        Chats chats = new Chats(
//                getCurrentDate(),
//                text,
//                "",
//                "TEXT",
//                firebaseUser.getUid()
//                ,receiveID
//        );

        Chats chats = new Chats(
                getCurrentDate(),
                text,
                "",
                "TEXT",
                senderID
                ,receiveID
        );

//        saveFirebaseData(chats);



        reference.child("Chats").push().setValue(chats)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Send","OnSuccess : ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Send","OnFailure : "+e.getMessage());
                    }
                });

        //add to chatList
        DatabaseReference chetRef1 = FirebaseDatabase.getInstance().getReference("Chats").child(senderID).child(receiveID);
        chetRef1.child("chatid").setValue(receiveID);

        //
        DatabaseReference chetRef2 = FirebaseDatabase.getInstance().getReference("Chats").child(receiveID).child(senderID);
        chetRef2.child("chatid").setValue(firebaseUser.getUid());
    }

    private void saveFirebaseData(Chats chats) {

        String timestamp = ""+System.currentTimeMillis();

//        Toast.makeText(this, ""+user_type, Toast.LENGTH_SHORT).show();

        //save to do
        FirebaseFirestore.getInstance().collection("Chats")
                .document(senderID)
                .set(chats)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        db update

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

//        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Chats");
//        ref1.child(senderID).setValue(chats)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        //db update
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //failed updating db
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
    }

    public String getCurrentDate(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String today = formatter.format(date);

        Calendar currentDateTime = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String currentTime = dateFormat.format(currentDateTime.getTime());

        return today+" "+currentTime;
    }
}
