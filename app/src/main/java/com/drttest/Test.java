// powered bu SCUDRT
import com.alibaba.fastjson.*;

public class Test{
    public static void main(String[] args){
        //create an account
        JSONObject json = new JSONObject();
        json.put("userID", "scudrt");
        json.put("password", "123456");
        boolean result = UserAccountClientManager.getInstance().signup(json);
        if (result){
            System.out.println("sign up succeed");
        }else{
            System.out.println("sign up failed");
        }

        result = UserAccountClientManager.getInstance().login(json);
        if (result){
            System.out.println("login succeed");
            //send messages below
            /*
            ChatMessage msg = new ChatMessage("hello drt!");
            msg.setSenderID("scudrt");
            msg.setReceiverID("scudrt");
            json = new JSONObject();
            json.put("MSGType", "SEND_TO");
            json.put("message", msg);
            SimpleClient client = new SimpleClient();
            if (client.isConnected()){
                client.send(json.toJSONString());
                System.out.println("message sent");
                System.out.println(json.toJSONString());
                System.out.println(client.get());
            }
            */
            //get messages below
            json = new JSONObject();
            json.put("MSGType", "ASK_MESSAGE");
            json.put("userID", "scudrt");
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
}
