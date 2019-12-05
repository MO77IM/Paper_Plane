package com.paperplane;

import com.alibaba.fastjson.JSONObject;

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
    public boolean signup(JSONObject json){
        json.put("MSGType", "SIGN_UP");
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
    public boolean login(JSONObject json){
        json.put("MSGType", "LOGIN");

        SimpleClient client = new SimpleClient();
        if (client.isConnected()){
            client.send(json.toJSONString());
            json = JSONObject.parseObject(client.get());
            if (json.getString("userID") != null){
                this.user = new UserAccount(json);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
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