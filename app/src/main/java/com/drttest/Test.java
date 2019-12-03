// powered bu SCUDRT
package com.drttest;
import java.util.*;
import com.alibaba.fastjson.*;

public class Test{
    public static void main(String[] args){
        JSONObject json = new JSONObject();
        json.put("userID", "邓瑞韬啊");
        json.put("password", "123456");
        System.out.println(json.toJSONString());

        boolean result = UserAccountManager.getInstance().signup(json.toJSONString());
        if (result){
            System.out.println("signup");
            result = UserAccountManager.getInstance().login(json.toJSONString());
            if (result){
                System.out.println("login");
            }
        }
        /*
        SimpleClient client = new SimpleClient();
        if (client.isConnected()){
            System.out.println("connect succeed!!");
            client.send("trnb!!!");
            System.out.println("server: " + client.get());
        }*/
    }
}
