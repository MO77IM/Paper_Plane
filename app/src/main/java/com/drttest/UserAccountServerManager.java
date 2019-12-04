package com.drttest;
import com.alibaba.fastjson.*;
import java.util.*;
import java.io.*;

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

    public static UserAccountServerManager getInstance(){
        return instance;
    }

    /**
     * @Description
     * Sign up for the new user, return sign up result true if succeed
     */
    public String signup(JSONObject json){
        //TODO: save the sign up record in the server
        JSONObject res = new JSONObject();
        res.put("result", false);
        String id = json.getString("userID");
        if (id != null) {
            if (this.getUserByID(id) == null) { //user not exists
                //TODO: check if the password is legal enough
                String pwd = json.getString("password");
                if (pwd != null && pwd.length() >= 6) { //ok
                    UserAccount newUser = new UserAccount(id, pwd);
                    this.users.put(id, newUser);
                    //TODO: should only save new user
                    this.saveUsers();
                    res.put("result", true);
                }
            }
        }
        return res.toJSONString();
    }

    /**
     * @Description
     * return login result true if succeed
     */
    public String login(JSONObject json){
        //TODO: save the login record in the server
        JSONObject res = new JSONObject(); //response
        String id = json.getString("userID");

        if (id != null){ //legal request
            UserAccount user = this.getUserByID(id);
            if (user != null){ //user exists
                if (user.getPassword().equals(json.getString("password"))){
                    user.setOnlineIP(json.getString("onlineIP"));
                    // return user's message to the client
                    res.put("userID", user.getUserID());
                    res.put("birthday", user.getBirthday());
                    res.put("nickname", user.getNickname());
                    res.put("signupTime", user.getSignupTime());
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
        try{
            //get file content
            FileReader f = new FileReader("./UserAccounts.xml");
            UserAccount user;
            String str = "";
            int n;
            while ((n = f.read()) != -1){
                str += (char)n;
            }
            //reconstructing user-map
            JSONObject json = JSONObject.parseObject(str);
            n = json.getInteger("size"); //get users' count
            for (int i=0;i<n;++i){
                str = "user" + i;
                user = new UserAccount(json.getJSONObject(str));
                this.users.put(user.getUserID(), user);
            }
            //log
            System.out.println("file loaded, " + n + " users found.");
            return true;
        }catch(IOException e){
            System.out.println("user file no found");
            return false;
        }
    }

    //save all users into the file
    private boolean saveUsers(){
        try{
            FileWriter f = new FileWriter("./UserAccounts.xml");
            JSONObject json = new JSONObject();
            int n = this.users.size();
            json.put("size", n);
            Iterator<Map.Entry<String, UserAccount>> entries = this.users.entrySet().iterator();
            for (int i=0;i<n;++i){
                Map.Entry<String, UserAccount> it = entries.next();
                json.put("user" + i, it.getValue());
            }
            f.write(json.toJSONString());
            f.flush();
            f.close();
            System.out.println("saved " + n + " users");
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //singleton
    private static UserAccountServerManager instance = new UserAccountServerManager();

    //store the Users' list
    private HashMap<String, UserAccount> users;
}
