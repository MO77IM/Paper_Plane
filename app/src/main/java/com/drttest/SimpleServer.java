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
    static final String SERVER_IP = "127.0.0.1"; // "47.103.198.96";
    static final int SERVER_PORT = 3000; // 3000
    static final int DEFAULT_TIMEOUT = 10000; // 10 seconds
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

    public void run(){
        System.out.println("server is running...");
        while (true){
            try{
                while (true){
                    Socket server = this.serverSocket.accept();
                    //TODO: save the message in the files
                    System.out.println("\n" + (new Date()).toString() + " :");
                    System.out.println("connected to " + server.getRemoteSocketAddress());

                    DataInputStream input = new DataInputStream(server.getInputStream());
                    DataOutputStream output = new DataOutputStream(server.getOutputStream());
                    String res = "";
                    
                    JSONObject loader = JSONObject.parseObject(input.readUTF());
                    String type = loader.getString("MSGType");
                    if (type != null){
                        // check the message type
                        if (type.equals("signup")){
                            res = UserAccountServerManager.getInstance().signup(loader.toJSONString());
                            output.writeUTF(res);
                        }else if (type.equals("login")){
                            res = UserAccountServerManager.getInstance().login(loader.toJSONString());
                            output.writeUTF(res);
                        }else if (type.equals("hello")) {
                            output.writeUTF("Hello from server.");
                        }
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
