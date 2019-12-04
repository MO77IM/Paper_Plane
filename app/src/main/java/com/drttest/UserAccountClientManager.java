package com.drttest;
import com.alibaba.fastjson.*;
import com.paperplane.Server;

/**
 * @Author scudrt
 * @Description
 * be used in client
 */
public class UserAccountClientManager {
    private UserAccountClientManager(){}

    /**
     * PUBLIC
     */

    public static UserAccountClientManager getInstance(){
        return instance;
    }

    /**
     * @Description
     * post a signup request to the server
     */
    public boolean signup(String signupMSG){
        JSONObject json = JSONObject.parseObject(signupMSG);
        json.put("messageType", "signup");
        SimpleClient client = new SimpleClient();
        if (client.isConnected()){
            client.send(json.toJSONString());
            json = JSONObject.parseObject(client.get());
            return json.getBoolean("result") == true;
        }else{
            return false;
        }
    }

    /**
     * @Description
     * try to post a login request to the server
     */
    public boolean login(String loginMSG){
        JSONObject json = JSONObject.parseObject(loginMSG);
        json.put("MSGType", "login");

        SimpleClient client = new SimpleClient();
        if (client.isConnected()){
            client.send(json.toJSONString());
            json = JSONObject.parseObject(client.get());
            if (json.getString("userID") != null){
                this.user = new UserAccount(json);
                this.user.setOnline(true);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    /**
     * PRIVATE
     */
    // use singleton mode
    private static UserAccountClientManager instance = new UserAccountClientManager();

    //user on client
    private UserAccount user;
}
