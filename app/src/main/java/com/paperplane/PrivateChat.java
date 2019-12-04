package com.paperplane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrivateChat implements Serializable {
    private boolean isGetMessage;

    private UserAccount targetUser;

    private ArrayList<Message> messages;

    public PrivateChat(){
        isGetMessage = false;
        messages = new ArrayList<Message>();
        targetUser = new UserAccount();
    }

    public PrivateChat(UserAccount targetUser){
        this.targetUser = targetUser;
        isGetMessage = false;
        messages = new ArrayList<Message>();
        targetUser = targetUser;
    }

    public boolean isGetMessage() {
        return isGetMessage;
    }

    public UserAccount getTargetUser() {
        return targetUser;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void AddMessage(Message message){
        messages.add(message);
    }

    public void SendMessage(Message message){
        AddMessage(message);
    }
}
