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
        targetUser = new UserAccount();
    }

    public boolean IsGetMessage() {
        return isGetMessage;
    }

    public UserAccount GetTargetUser() {
        return targetUser;
    }

    public ArrayList<Message> GetMessages() {
        return messages;
    }

    public void AddMessage(Message message){
        messages.add(message);
    }

    public void SendMessage(Message message){
        AddMessage(message);
    }
}
