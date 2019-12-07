import java.util.*;
import java.net.*;
import java.io.*;

public class ClientObserver extends Thread{
    public ClientObserver(Socket client, DataInputStream input, DataOutputStream output){
        this.client = client;
    }
    public void run(){
        try{
            if (!this.client.isClosed()){
                if (this.input != null){
                    this.input.close();
                    this.output.close();
                    this.client.close();
                }
            }
        }catch(IOException ioe){
            ;
        }
    }

    /**
     * PRIVATE
     */
    private Socket client;
    private DataOutputStream output;
    private DataInputStream input;
}