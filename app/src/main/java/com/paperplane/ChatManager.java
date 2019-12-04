package com.paperplane;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

public class ChatManager {
    private ArrayList<PrivateChat> chatList;
    private PrivateChat currentChat;

    private NetworkService.NetworkBinder networkBinder;

    static private ChatManager instance;

    private ChatManager(){
        chatList = new ArrayList<PrivateChat>();
        currentChat = null;
    }

    static public ChatManager getInstance(){
        if(instance == null){
            instance = new ChatManager();
        }
        return instance;
    }

    public void setCurrentChat(PrivateChat currentChat) {
        this.currentChat = currentChat;
    }

    public PrivateChat getCurrentChat() {
        return currentChat;
    }

    public void startChat(UserAccount targetUser){
        PrivateChat privateChat = new PrivateChat(targetUser);
        chatList.add(privateChat);
    }

    public void delChat(PrivateChat privateChat){
        chatList.remove(privateChat);
    }

    public ArrayList<PrivateChat> getChatList() {
        return chatList;
    }

    public void setNetworkBinder(NetworkService.NetworkBinder networkBinder) {
        this.networkBinder = networkBinder;
    }

    public PrivateChat getChatByUser(UserAccount user){
        for(PrivateChat chat: chatList){
            if(chat.getTargetUser() == user){
                return chat;
            }
        }
        return null;
    }

    public PrivateChat getChatByUserId(String id){
        for(PrivateChat chat: chatList){
            if(chat.getTargetUser().getUserID().equals(id));
                return chat;
        }
        return null;
    }

    public int getChatSize(){
        return chatList.size();
    }

    public PrivateChat getChatByPosition(int position){
        if(position >= getChatSize() || position < 0){
            return null;
        }
        return chatList.get(position);
    }

    public void sendTextMessageInChat(PrivateChat privateChat, String msg){
        privateChat.SendTextMessage(msg);

        Log.d("ChatManager", (new Boolean(networkBinder == null)).toString());

        JSONObject json = new JSONObject();
        try {
            json.put("MSGType", "SEND_TO");
            json.put("content",msg);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(networkBinder != null)
            networkBinder.SendMessage(json.toString());
    }
}
