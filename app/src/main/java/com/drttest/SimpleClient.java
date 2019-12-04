//powered by SCUDRT
package com.drttest;
import java.util.*;
import java.net.*;
import java.io.*;
import com.alibaba.fastjson.*;

public class SimpleClient{
    static final String SERVER_IP = "47.103.198.96"; // "47.103.198.96";
    static final int SERVER_PORT = 3000; // 3000
    public SimpleClient(){
        this.connect(SERVER_IP, SERVER_PORT);
    }
    public SimpleClient(String _serverIP, int _port){
        this.connect(_serverIP, _port);
    }

    public boolean connect(String _serverIP, int _port){
        // return false if connection failed
        try{
            this.clientSocket = new Socket(_serverIP, _port);
        }catch (UnknownHostException uhe){
            uhe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

        try{
            if (this.isConnected()){
                this.input = new DataInputStream(this.clientSocket.getInputStream());
                this.output = new DataOutputStream(this.clientSocket.getOutputStream());
                return true;
            }else{
                return false;
            }
        }catch (IOException err){
            err.printStackTrace();
        }
        return false;
    }

    public boolean isConnected(){
        // return false if connection failed
        if (this.clientSocket != null){
            return this.clientSocket.getRemoteSocketAddress() != null;
        }else{
            return false;
        }
    }

    public boolean send(String _data){
        // send string to the server
        try{
            if (this.output != null){
                this.output.writeUTF(_data);
                return true;
            }else{
                return false;
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return false;
    }

    public String get(){
        //get string from server
        try{
            if (this.input != null){
                return this.input.readUTF();
            }else{
                return null;
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

    public void close(){
        try{
            this.input.close();
            this.output.close();
            this.clientSocket.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

        this.input = null;
        this.output = null;
        this.clientSocket = null;
    }

    /** PRIVATE */
    private Socket clientSocket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
}
