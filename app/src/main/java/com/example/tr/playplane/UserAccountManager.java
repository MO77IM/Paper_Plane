package com.example.tr.playplane;//powered by SCUDRT

import java.util.ArrayList;

public class UserAccountManager{
    private UserAccountManager(){
        this.users = new ArrayList<UserAccount>();
        // this.loadFile();
    }

    /** PUBLIC */
    public static UserAccountManager getInstance(){
        return instance;
    }
    
    public boolean signup(SimpleJSON signupMSG){
        String id = signupMSG.getValue("userID");
        if (this.getUserByID(id) != null){
            return false;
        }

        UserAccount temp = new UserAccount(id, signupMSG.getValue("password"));
        this.users.add(temp);
        return true;
    }
    public boolean signup(String signupMSG){
        return this.signup(new SimpleJSON(signupMSG));
    }


    public boolean login(SimpleJSON loginMSG){
        String id = loginMSG.getValue("userID");
        String password = loginMSG.getValue("password");
        UserAccount user = this.getUserByID(id);
        if (user == null){
            return false;
        }else if (user.getPassword().equals(password) == false){
            return false;
        }else{
            user.setOnline(true);
            return true;
        }
    }
    public boolean login(String loginMSG){
        return this.login(new SimpleJSON(loginMSG));
    }


    /** PRIVATE */
    public UserAccount getUserByID(String id){
        int n = this.users.size();
        UserAccount now;
        for (int i=0;i<n;++i){
            now = this.users.get(i);
            if (now.getUserID().equals(id)){
                return now;
            }
        }
        return null;
    }
    // use singleton mode
    private static UserAccountManager instance = new UserAccountManager();

    private ArrayList<UserAccount> users;
}
