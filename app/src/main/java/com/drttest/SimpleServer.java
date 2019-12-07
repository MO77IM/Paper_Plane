//powered by SCUDRT
package com.drttest;
import java.util.*;
import java.net.*;
import java.io.*;
import com.alibaba.fastjson.*;

/**
    usage:
        Thread t = new Thread(new SimpleServer());
        t.start();
 */
public class SimpleServer implements Runnable{
    static final int SERVER_PORT = 3000; // 3000
    static final int DEFAULT_TIMEOUT = 60000; // 60 seconds

    public SimpleServer(){
        this._init(SERVER_PORT);
    }
    public SimpleServer(int _port){
        this._init(_port);
    }
    private void _init(int _port){
        try{
            this.serverSocket = new ServerSocket(_port);
            this.setTimeout(DEFAULT_TIMEOUT);
            Logger.log("Server listening on port " + _port);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /** PUBLIC */
    public void setTimeout(int _timeout){
        try{
            this.serverSocket.setSoTimeout(_timeout);
        }catch (SocketException se){
            se.printStackTrace();
        }
    }

    /**
     * main function of the server
     */
    public void run(){
        Socket server;
        DataInputStream input;
        DataOutputStream output;
        JSONObject loader;
        String res, type;
        while (true){
            try{
                while (true){
                    server = this.serverSocket.accept();
                    input = new DataInputStream(server.getInputStream());
                    output = new DataOutputStream(server.getOutputStream());
                    
                    //convert input message to JSON
                    res = input.readUTF();
                    loader = JSONObject.parseObject(res);

                    //log message
                    Logger.log("new connection from "+
                                server.getRemoteSocketAddress() + " :\n"+
                                "message: " + res);
                    res = "";

                    //check the type of message
                    type = loader.getString("MSGType");
                    if (type != null){
                        // check the message type
                        if (type.equals("ASK_MESSAGE")){ //request for new messages
                            Thread pushThread = new Thread(new PushMessageDelegate(server, output, loader));
                            pushThread.start();
                            break;
                            //res = ChatServerManager.getInstance().getOfflineChatMessage(loader);
                        }else if (type.equals("SEND_TO")){
                            res = ChatServerManager.getInstance().addOfflineChatMessage(loader);
                        }else if (type.equals("GET_ONLINE_USERS")){
                            res = UserAccountServerManager.getInstance().getOnlineUsers();
                        }else if (type.equals("SIGN_UP")){
                            res = UserAccountServerManager.getInstance().signup(loader);
                        }else if (type.equals("LOGIN")){
                            loader.put("onlineIP", server.getRemoteSocketAddress());
                            res = UserAccountServerManager.getInstance().login(loader);
                        }else if (type.equals("PING")) {
                            res = "Hello from server " + server.getLocalSocketAddress();
                        }
                        output.writeByte(0); //confirm byte
                        output.writeUTF(res);
                    }
                    server.close();
                }
            }catch (IOException ioe){
                // ioe.printStackTrace();
            }
        }
    }

    /** PRIVATE */
    private ServerSocket serverSocket = null;
}
