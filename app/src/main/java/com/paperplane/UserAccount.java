package com.paperplane;//powered by SCUDRT

import java.util.Date;

public class UserAccount{
    public UserAccount(){
        this.signupTime = new Date();
        this.birthday = this.signupTime;
        this.isOnline = false;
        this.nickName = "";
    }
    public UserAccount(String _id, String _pwd){
        this.signupTime = new Date();
        this.birthday = this.signupTime;
        this.isOnline = false;
        this.nickName = "";
        this.setUserID(_id);
        this.setPassword(_pwd);
    }
    
    /** PUBLIC */
    public String getUserID(){
        return this.userID;
    }
    public String getPassword(){
        return this.password;
    }
    public String getNickname(){
        return this.nickName;
    }
    public Date getBirthday(){
        return this.birthday;
    }
    public Date getSignupTime(){
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
        this.nickName = _nickname;
    }
    public void setBirthday(Date _birthday){
        this.birthday = _birthday;
    }
    public void setOnline(boolean _isOnline){
        this.isOnline = _isOnline;
    }

    /** PRIVATE */
    private String userID, password;

    private String nickName;

    private Date birthday;

    private Date signupTime;

    private boolean isOnline;
}
