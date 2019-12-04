package com.paperplane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrivateChat {
    private boolean isGetMessage;

    private UserAccount targetUser;

    private ArrayList<ChatMessage> messages;

    public PrivateChat(){
        isGetMessage = false;
        messages = new ArrayList<ChatMessage>();
        targetUser = new UserAccount();
    }

    public PrivateChat(UserAccount targetUser){
        this.targetUser = targetUser;
        isGetMessage = false;
        messages = new ArrayList<ChatMessage>();
        targetUser = targetUser;
    }

    public boolean isGetMessage() {
        return isGetMessage;
    }

    public UserAccount getTargetUser() {
        return targetUser;
    }

    public ArrayList<ChatMessage> getMessages() {
        return messages;
    }

    public void AddMessage(ChatMessage message){
        messages.add(message);
    }

    public void SendTextMessage(String content){
        ChatMessage msg = new ChatMessage(targetUser.getIcon(), content, "", ChatMessage.SEND);
        this.AddMessage(msg);
    }

    public void ReceiveTextMessage(String content){
        ChatMessage msg = new ChatMessage(R.mipmap.ic_launcher, content, "", ChatMessage.RECEIVE); //之后改为本地用户的头像
        this.AddMessage(msg);
    }

    public int getMessageSize(){
        return messages.size();
    }
}
