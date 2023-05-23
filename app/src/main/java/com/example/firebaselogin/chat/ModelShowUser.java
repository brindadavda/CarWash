package com.example.firebaselogin.chat;

public class ModelShowUser {

    private String senderID, receiverID ,Name , PhoneNo;

    public ModelShowUser(String senderID,String receiverID ,String Name , String PhoneNo) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.Name = Name;
        this.PhoneNo = PhoneNo;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String userName) {
        this.Name = userName;
    }
}
