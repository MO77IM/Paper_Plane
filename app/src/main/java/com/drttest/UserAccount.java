//powered by SCUDRT
package com.drttest;
import com.alibaba.fastjson.*;
import java.util.*;

public class UserAccount{
    public UserAccount(String _id, String _pwd){
        this.signupTime = new Date().toString();
        this.birthday = this.signupTime;
        this.userID = _id;
        this.password = _pwd;
    }
    public UserAccount(JSONObject userJSON){
        this.signupTime = userJSON.getString("signupTime");
        this.birthday = userJSON.getString("birthday");
        this.userID = userJSON.getString("userID");
        this.nickname = userJSON.getString("nickname");
    }
    
    /** PUBLIC */
    public String getUserID(){
        return this.userID;
    }
    public String getPassword(){
        return this.password;
    }
    public String getNickname(){
        return this.nickname;
    }
    public String getBirthday(){
        return this.birthday;
    }
    public String getSignupTime(){
        return this.signupTime;
    }
    public boolean isOnline(){
        return this.isOnline;
    }

    public void setUserID(String _userID){
        this.userID = _userID;
    }
    public void setPassword(String _password){
        this.password = _password;
    }
    public void setNickname(String _nickname){
        this.nickname = _nickname;
    }
    public void setBirthday(String _birthday){
        this.birthday = _birthday;
    }
    public void setSignupTime(String _signupTime){this.signupTime = _signupTime;}
    public void setOnline(boolean _isOnline){
        this.isOnline = _isOnline;
    }

    /** PRIVATE */
    private String userID, password;

    private String nickname;

    private String birthday;

    private String signupTime = new Date().toString();

    private boolean isOnline = false;
}
