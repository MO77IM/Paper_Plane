package com.drttest;
import com.alibaba.fastjson.*;
import com.paperplane.Server;

/**
 * @Author scudrt
 * @Description
 * be used in client
 */
public class UserAccountClientManager {
    private UserAccountClientManager(){
        this.isOnline = false;
    }

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
        SimpleClient client = new SimpleClient();
        if (client.isConnected()){
            client.send(signupMSG);
            JSONObject res = JSONObject.parseObject(client.get());
            return res.getBoolean("result") == true;
        }else{
            return false;
        }
    }
    public boolean signup(JSONObject signupJSON){
        return this.signup(signupJSON.toJSONString());
    }

    /**
     * @Description
     * try to post a login request to the server
     */
    public boolean login(JSONObject loginJSON){
        return this.login(loginJSON.toJSONString());
    }
    public boolean login(String loginMSG){
        SimpleClient client = new SimpleClient();
        if (client.isConnected()){
            client.send(loginMSG);
            JSONObject res = JSONObject.parseObject(client.get());
            if (res.getString("userID") != null){
                this.user = new UserAccount(res);
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
