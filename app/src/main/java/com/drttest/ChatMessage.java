package com.drttest;

import java.util.*;
import jdk.nashorn.internal.objects.annotations.Getter;

public class ChatMessage{
    public ChatMessage(){
        this.sendTime = new Date().toString();
    }
    public ChatMessage(String message){
        this.sendTime = new Date().toString();
        this.message = message;
    }
    
    /**
     * PUBLIC
     * normal getters and setters
     */
    public void setMessage(String message){
        this.message = message;
    }
    public void setSenderID(String senderID){
        this.senderID = senderID;
    }
    public void setReceiverID(String receiverID){
        this.receiverID = receiverID;
    }

    public String getMessage(){
        return this.message;
    }
    public String getReceiverID(){
        return this.receiverID;
    }
    public String getSenderID(){
        return this.senderID;
    }
    public String getSendTime(){
        return this.sendTime;
    }

    /**
     * PRIVATE
     */
    private String message;
    private String senderID;
    private String receiverID;
    private String sendTime;
}
