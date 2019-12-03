package com.paperplane;//powered by SCUDRT

import com.alibaba.fastjson.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

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
                    System.out.println("\n" + (new Date()).toString() + " :");
                    System.out.println("connected to " + server.getRemoteSocketAddress());

                    DataInputStream input = new DataInputStream(server.getInputStream());
                    DataOutputStream output = new DataOutputStream(server.getOutputStream());
                    
                    JSONObject loader = JSONObject.parseObject(input.readUTF());
                    

                    server.close();
                }
            }catch (IOException ioe){
                //ioe.printStackTrace();
            }
        }
    }

    /** PRIVATE */
    private ServerSocket serverSocket = null;
}
