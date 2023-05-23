package com.example.firebaselogin.chat;

public class ModelChat {
    String message , receiverId , senderId , Timestamp;

    public ModelChat(String message, String receiverId, String senderId,String Timestamp) {
        this.message = message;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.Timestamp = Timestamp;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
