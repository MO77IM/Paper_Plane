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

    /**
     * @Description
     * Sign up for the new user, return sign up result true if succeed
     */
    public String signup(String signupStr){
        //TODO: save the sign up record in the server
        JSONObject res = new JSONObject();
        JSONObject json = JSONObject.parseObject(signupStr);

        String id = json.getString("userID");
        if (id != null) {
            if (this.getUserByID(id) == null) { //user not exists
                //TODO: check if the password is legal enough
                String pwd = json.getString("password");
                if (pwd != null && pwd.length() >= 6) { //ok
                    UserAccount newUser = new UserAccount(id, pwd);
                    //TODO: save new user in the file
                    res.put("signup result", true);
                }
            }
        }
        return res.toJSONString();
    }

    /**
     * @Description
     * return login result true if succeed
     */
    public String login(String loginMSG){
        //TODO: save the login record in the server
        JSONObject res = new JSONObject(); //response
        JSONObject json = JSONObject.parseObject(loginMSG); //resolve the request string
        String id = json.getString("userID");

        if (id != null){ //legal request
            UserAccount user = this.getUserByID(id);
            if (user != null){ //user exists
                if (user.getPassword().equals(json.getString("password"))){
                    user.setOnline(true);
                    res.put("login result", true);
                }
            }
        }
        return res.toJSONString();
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
    private boolean loadUserAccountFiles(){
        //TODO
        return true;
    }

    private boolean saveUser(String userInfo){
        //TODO
        return true;
    }

    //singleton
    private static UserAccountServerManager instance = new UserAccountServerManager();

    //store the Users' list
    private HashMap<String, UserAccount> users;
}
