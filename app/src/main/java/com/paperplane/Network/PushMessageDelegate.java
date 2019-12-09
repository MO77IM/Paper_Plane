package com.paperplane.Network;

import com.alibaba.fastjson.JSONObject;
import com.paperplane.Logger;
import com.paperplane.Manager.ChatServerManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * @Author
 * scudrt
 * @Description
 * created to wait and push message for server
 */
public class PushMessageDelegate implements Runnable{
    public PushMessageDelegate(Socket server, DataOutputStream output, JSONObject json){
        this.server = server;
        this.output = output;
        this.message = json;
    }
    /**
     * PUBLIC
     */

    public void run(){
        String id = this.message.getString("userID");
        try{
            while (!ChatServerManager.getInstance().hasMessage(id)){
                //don't be too busy
                Thread.currentThread().sleep(500);
            }
            //send message
            try{
                if (!server.isClosed()){
                    //check if the user is still connecting
                    this.output.writeByte(0); //confirm byte
                    this.output.writeUTF(ChatServerManager.getInstance().getOfflineChatMessage(id));
                    Logger.log("offline messages sent to " + id);
                }else{
                    Logger.log("user " + id + "closed the connection");
                }
            }catch(SocketException se){
                Logger.log("failed to send offline messages to \'" + id + "\' because of bad connection");
            }
            this.output.close();
            this.server.close();
            return;
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * PRIVATE
     */
    private Socket server;
    private DataOutputStream output;
    private JSONObject message;
}