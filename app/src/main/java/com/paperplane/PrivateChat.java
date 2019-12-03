package com.paperplane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrivateChat implements Serializable {
    private UserAccount targetUser;
    private boolean isGetMessage;

    private List<String> messages;

    public PrivateChat(UserAccount targetUser){
        this.targetUser = targetUser;
        isGetMessage = false;
        messages = new ArrayList<String>();
    }

    public boolean IsGetMessage() {
        return isGetMessage;
    }

    public UserAccount GetTargetUser() {
        return targetUser;
    }

    public List<String> GetMessages() {
        return messages;
    }

    public void AddMessage(Message message){
        messages.add(message.toJSON());
    }

    public void AddMessage(String message){
        messages.add(message);
    }
}
