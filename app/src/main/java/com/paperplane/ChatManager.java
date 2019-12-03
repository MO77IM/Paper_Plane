package com.paperplane;

import java.util.ArrayList;

public class ChatManager {
    private ArrayList<PrivateChat> chatList;

    static private ChatManager instance;

    private ChatManager(){
        chatList = new ArrayList<PrivateChat>();
    }

    static public ChatManager getInstance(){
        if(instance == null){
            instance = new ChatManager();
        }
        return instance;
    }

    public void startChat(UserAccount targetUser){
        PrivateChat privateChat = new PrivateChat(targetUser);
        chatList.add(privateChat);
    }

    public void delChat(PrivateChat privateChat){
        chatList.remove(privateChat);
    }
}
