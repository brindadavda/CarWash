package com.example.firebaselogin.chat;

import java.util.List;

public interface OnReadChatCallBack {

    void onReadSuccess(List<Chats> list);
    void onReadFailed();
}
