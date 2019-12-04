package com.drttest;

import com.alibaba.fastjson.*;
import java.util.HashMap;

/**
* @Author scudrt
* @Description
* be used in the server
* */
public class UserAccountServerManager {
    /**
     * CONSTRUCTORS
     */
    private UserAccountServerManager(){
        this.users = new HashMap<String, UserAccount>();
        this.loadUserAccountFiles(); // if we have it
    }

    /**
     * PUBLIC
     */

    public UserAccountServerManager getInstance(){
        return instance;
    }

    public String signup(JSONObject signupJSON){
        JSONObject res = new JSONObject();

        String id = signupJSON.getString("userID");
        if (id != null) {
            if (this.getUserByID(id) == null) { //user not exists
                //TODO: check if the password is legal enough
                String pwd = signupJSON.getString("password");
                if (pwd != null && pwd.length() >= 6) { //ok
                    UserAccount newUser = new UserAccount(id, pwd);
                    res.put("result", true);
                }
            }
        }
        return res.toJSONString();
    }
    public String signup(String signupStr){
        return this.signup(JSONObject.parseObject(signupStr));
    }

    //TODO: complete login() function in server version
    public String login(JSONObject loginJSON){
        String id = loginJSON.getString("userID");
        String password = loginJSON.getString("password");
        UserAccount user = this.getUserByID(id);
        if (user == null){ //user not exists
            return false;
        }else if (!user.getPassword().equals(password)){ //wrong password
            return false;
        }else{
            user.setOnline(true);
            return true;
        }
    }
    public String login(String loginMSG){
        return this.login(JSON.parseObject(loginMSG));
    }

    public UserAccount getUserByID(String id){
        return this.users.get(id);
    }

    /**
     * PRIVATE
     */

    /**
     * load local files contain users' information
     */
    private void loadUserAccountFiles(){
        ;
    }

    //singleton
    private static UserAccountServerManager instance = new UserAccountServerManager();

    //store the Users' list
    private HashMap<String, UserAccount> users;
}
