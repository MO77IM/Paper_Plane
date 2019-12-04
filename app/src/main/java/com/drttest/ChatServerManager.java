package com.drttest;

import com.alibaba.fastjson.*;
import java.util.*;

/**
 * @Description
 * manage and distribute users' chatting messages for server
 */
public class ChatServerManager{
    private ChatServerManager(){
        this.messageKeeper = new HashMap<String, ArrayList<ChatMessage>>();
    }

    /**
     * PUBLIC
     */

     public ChatServerManager getInstance(){
         return instance;
     }

     /**
      * @Description
      * save offline chatting messages for users
      */
     public String addOfflineChatMessage(JSONObject chatJSON){
         String receiverID = chatJSON.getString("receiverID");
         String senderID = chatJSON.getString("senderID");
         JSONObject res = new JSONObject();
         res.put("result", false)

         //check if sender and receiver exists
         UserAccountServerManager instance = UserAccountServerManager.getInstance();
         if (instance.getUserByID(receiverID) != null){ //exists
             if (instance.getUserByID(senderID) != null){
                 //save message for the receiver
                 ArrayList<ChatMessage> messages = this.messageKeeper.get(receiverID);
                 if (messages == null){
                     messages = new ArrayList<ChatMessage>();
                     this.messageKeeper.put(receiverID, messages);
                 }
                 messages.add(new ChatMessage(chatJSON));
                 res.put("result", true);
             }
         }
         return res.toJSONString();
     }

     /**
      * @Description
      * return user's offline chatting message
      */
    public String getOfflineChatMessage(JSONObject json){
        String id = json.getString("userID");
        JSONObject res = new JSONObject();
        res.put("size", 0);

        //take all user's messages out
        if (id != null){
            ArrayList<ChatMessage> messages = this.messageKeeper.get(id);
            if (messages != null && messages.size() > 0){
                int n = messages.size();
                ChatMessage temp;
                res.put("size", n);
                //put messages into response json
                for (int i=0;i<n;++i){
                    temp = messages.get(i);
                    res.put("message"+i, temp);
                    messages.remove(i);
                }
                //log
                System.out.println(res.toJSONString());
            }
        }
        return res.toJSONString();
    }

    /**
     * PRIVATE
     */
    private static ChatServerManager instance = new ChatServerManager();

    //keep offline messages for users, the key is userID
    private HashMap<String, ArrayList<ChatMessage>> messageKeeper;
}
