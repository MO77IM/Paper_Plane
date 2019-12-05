//powered by SCUDRT
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
            System.out.println("Server listening on port " + _port);
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
                    //TODO: save the message in the files
                    System.out.println("\n" + (new Date()).toString() + ":");
                    System.out.println("connected to " + server.getRemoteSocketAddress());

                    input = new DataInputStream(server.getInputStream());
                    output = new DataOutputStream(server.getOutputStream());
                    res = ""; //response string
                    
                    loader = JSONObject.parseObject(input.readUTF());
                    type = loader.getString("MSGType");
                    if (type != null){
                        System.out.println("request: " + type);
                        // check the message type
                        if (type.equals("ASK_MESSAGE")){ //request for new messages
                            Thread pushThread = new Thread(new PushMessageDelegate(server, output, loader));
                            pushThread.start();
                            break;
                            //res = ChatServerManager.getInstance().getOfflineChatMessage(loader);
                        }else if (type.equals("SEND_TO")){
                            res = ChatServerManager.getInstance().addOfflineChatMessage(loader);
                        }else if (type.equals("SIGN_UP")){
                            res = UserAccountServerManager.getInstance().signup(loader);
                        }else if (type.equals("LOGIN")){
                            loader.put("onlineIP", server.getRemoteSocketAddress());
                            res = UserAccountServerManager.getInstance().login(loader);
                        }else if (type.equals("PING")) {
                            res = "Hello from server " + server.getLocalSocketAddress();
                        }
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
