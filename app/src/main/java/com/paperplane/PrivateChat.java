package com.paperplane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrivateChat implements Serializable {
    private boolean isGetMessage;

    private List<Message> messages;

    public PrivateChat(){
        isGetMessage = false;
        messages = new ArrayList<Message>();
    }

    public boolean IsGetMessage() {
        return isGetMessage;
    }

    //public UserAccount GetTargetUser() {
        //return targetUser;
    //}

    public List<Message> GetMessages() {
        return messages;
    }

    public void AddMessage(Message message){
        messages.add(message);
    }
}
