package com.example.firebaselogin.chat;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaselogin.LoginActivity;
import com.example.firebaselogin.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    //
    TextView nameTv , phoneTv;
    RecyclerView chatRecycle;
    EditText messageEt;
    ImageView backIv;
    ImageButton sendIb ;

    private String ReceiverID , senderID;
    private ChatsAdapter adapter = null ;
    private List<Chats> list = new ArrayList<>();
    String receiverPhone , receiverName;
    private boolean isActionShown = false;
    private ChatService chatService;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //
        init();

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initialize();
        initBtnClick();
        readChats();
    }

    private void init(){
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        chatRecycle = findViewById(R.id.chatRecycle);
        messageEt = findViewById(R.id.messageEt);
        sendIb = findViewById(R.id.sendIb);
        backIv = findViewById(R.id.backIv);
    }

    private void initialize(){
        Intent intent = getIntent();
        receiverName = intent.getStringExtra("receiverName");
        ReceiverID = intent.getStringExtra("receiverID");
        receiverPhone = intent.getStringExtra("receiverPhone");
        senderID = intent.getStringExtra("senderID");

        chatService = new ChatService(this,ReceiverID , senderID);

        if (ReceiverID != null ){
//            Toast.makeText(this, ""+ReceiverID, Toast.LENGTH_SHORT).show();
            nameTv.setText(receiverName);
            phoneTv.setText(receiverPhone);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this , RecyclerView.VERTICAL , false);
        layoutManager.setStackFromEnd(true);
        chatRecycle.setLayoutManager(layoutManager);
        adapter = new ChatsAdapter(list,this);
        chatRecycle.setAdapter(adapter);

    }

    private void readChats() {
        chatService.readChatData(new OnReadChatCallBack() {
            @Override
            public void onReadSuccess(List<Chats> list) {
                adapter.setList(list);
            }

            @Override
            public void onReadFailed() {
                Log.d("ChatsActivity" , "onReadFailed:");
            }
        });
    }

    private void initBtnClick(){

        sendIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(messageEt.getText().toString()))){
                    chatService.sendTextMsg(messageEt.getText().toString());
                    Toast.makeText(ChatActivity.this, "Message Send", Toast.LENGTH_SHORT).show();

                    messageEt.setText("");
                }
            }
        });
    }



}