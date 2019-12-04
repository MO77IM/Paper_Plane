package com.paperplane;//powered by SCUDRT

import java.io.Serializable;
import java.util.Date;

public class UserAccount{
    public UserAccount(){
        this.signupTime = new Date();
        this.birthday = this.signupTime;
        this.isOnline = false;
        this.nickName = "";
        this.icon = R.mipmap.ic_launcher;
    }
    public UserAccount(String _id, String _pwd){
        this.signupTime = new Date();
        this.birthday = this.signupTime;
        this.isOnline = false;
        this.nickName = "";
        this.setUserID(_id);
        this.setPassword(_pwd);
        this.icon = R.mipmap.ic_launcher;
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
    public int getIcon() { return icon; }

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
    public void setIcon(int icon) { this.icon = icon; }

    /** PRIVATE */

    //暂时使用Resource里面提供的默认图像，icon为整数代表资源的ID
    private int icon;

    private String userID, password;

    private String nickName;

    private Date birthday;

    private Date signupTime;

    private boolean isOnline;
}
