// powered bu SCUDRT
import com.alibaba.fastjson.*;

public class Test{
    public static void main(String[] args){
        JSONObject json = new JSONObject();

        json.put("userID", "trnb");
        json.put("password", "123456");
        boolean result = UserAccountClientManager.getInstance().login(json);
        if (result){
            System.out.println("login ok");
            SimpleClient client = new SimpleClient();
            if (client.isConnected()){
                json.put("MSGType", "GET_ONLINE_USERS");
                json.remove("userID");
                json.remove("password");
                client.send(json.toJSONString());
                System.out.println(client.get());
                client.close();
            }
        }
/*
        if (args[0].equals("send")){ //send message
            //sign up
            json.put("userID", "scudrt");
            json.put("password", "123456");
            boolean result = UserAccountClientManager.getInstance().signup(json);
            if (result){
                System.out.println("sign up succeed");
            }else{
                System.out.println("sign up failed");
            }
            //try to login
            result = UserAccountClientManager.getInstance().login(json);
            if (result){
                System.out.println("login succeed");
                //send messages below
                ChatMessage msg = new ChatMessage("你好啊，nb的唐睿！！");
                msg.setSenderID("scudrt");
                msg.setReceiverID("trnb");
                json = new JSONObject();
                json.put("MSGType", "SEND_TO");
                json.put("message", msg);
                SimpleClient client = new SimpleClient();
                if (client.isConnected()){
                    client.send(json.toJSONString());
                    System.out.println("message sent: " + json.toJSONString());
                    System.out.println("response: " + client.get());
                }
            }else{
                System.out.println("login failed");
            }
        }else if (args[0].equals("get")){ //try to get messages
            //sign up
            json.put("userID", "trnb");
            json.put("password", "123456");
            boolean result = UserAccountClientManager.getInstance().signup(json);
            if (result){
                System.out.println("sign up succeed");
            }else{
                System.out.println("sign up failed");
            }
            //try to login
            result = UserAccountClientManager.getInstance().login(json);
            if (result){
                System.out.println("login succeed");
                //get messages below
                json = new JSONObject();
                json.put("MSGType", "ASK_MESSAGE");
                json.put("userID", "trnb");
                SimpleClient client = new SimpleClient();
                if (client.isConnected()){
                    client.send(json.toJSONString());
                    System.out.println("message sent");
                    System.out.println(json.toJSONString());
                    System.out.println(client.get());
                }
            }else{
                System.out.println("login failed");
            }
        }
        */
    }
}