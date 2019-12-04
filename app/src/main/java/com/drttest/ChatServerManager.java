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

     /**
      * @Description
      * save offline chatting messages for users
      */
     public String delegateChatMessage(JSONObject chatJSON){
         String receiverID = chatJSON.getString("receiverID");
         String senderID = chatJSON.getString("senderID");
         JSONObject res = new JSONObject();

         //check if sender and receiver exists
         UserAccountServerManager instance = UserAccountServerManager.getInstance();
         if (instance.getUserByID(receiverID) != null){ //exists
             if (instance.getUserByID(senderID) != null){
                 //save message for the receiver
                 ArrayList<ChatMessage> messages = this.messageKeeper.get(receiverID);
                 if (messages == null){
                     messages = new ArrayList<ChatMessage>();
                 }
                 //messages.add(new ChatMessage(chatJSON));this.messageKeeper.put(receiverID, messages);
             }
         }
         return res.toJSONString();
     }

     /**
      * @Description
      * return user's offline chatting message
      */
     public String checkUserChatMessage(){
         return null;
     }

    /**
     * PRIVATE
     */
    private static ChatServerManager instance = new ChatServerManager();

    //keep offline messages for users, the key is userID
    private HashMap<String, ArrayList<ChatMessage>> messageKeeper;
}
