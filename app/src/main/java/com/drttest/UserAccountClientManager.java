package com.drttest;
import com.alibaba.fastjson.*;

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
    public JSONObject signup(JSONObject json){
        json.put("MSGType", "SIGN_UP");
        SimpleClient client = new SimpleClient();
        if (client.isConnected()){
            client.send(json.toJSONString());
            json = JSONObject.parseObject(client.get());
            client.close();
            return json;
        }
        client.close();
        return null;
    }

    /**
     * @Description
     * try to post a login request to the server
     */
    public boolean login(JSONObject json){
        json.put("MSGType", "LOGIN");

        SimpleClient client = new SimpleClient();
        if (client.isConnected()){
            client.send(json.toJSONString());
            json = JSONObject.parseObject(client.get());
            if (json.getString("userID") != null){
                this.user = new UserAccount(json);
                client.close();
                return true;
            }
        }
        client.close();
        return false;
    }

    public UserAccount getCurrentUser(){
        return this.user;
    }


    /**
     * PRIVATE
     */
    // use singleton mode
    private static UserAccountClientManager instance = new UserAccountClientManager();

    //user on client
    private UserAccount user;
}
