import java.util.*;
import com.alibaba.fastjson.*;

/**
 * @Author
 * scudrt
 * @Description
 * created to wait and push message for server
 */
public class PushMessageDelegate implements Runnable{
    public PushMessageDelegate(ServerSocket server, DataOutputStream output, JSONObject json){
        this.server = server;
        this.output = output;
        this.message = json.getString("message");
    }
    /**
     * PUBLIC
     */

    @Override
    public void run(){
        String id = this.message.getString("userID");
        while (!ChatServerManager.getInstance().hasMessage(id)){
            //don't be too busy
            Thread.currentThread().sleep(1000);
        }
        //send message
        output.writeUTF(ChatServerManager.getInstance().getOfflineChatMessage(message));
        server.close();
    }

    /**
     * PRIVATE
     */
    private ServerSocket server;
    private DataOutputStream output;
    private JSONObject message;
}