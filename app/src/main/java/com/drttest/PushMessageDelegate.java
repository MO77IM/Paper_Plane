import java.util.*;
import java.net.*;
import java.io.*;
import com.alibaba.fastjson.*;

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
                    Thread.currentThread().sleep(1000);
            }
            //send message
            this.output.writeUTF(ChatServerManager.getInstance().getOfflineChatMessage(id));
            this.server.close();
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