package com.example.tr.playplane;// powered bu SCUDRT

public class Test{
    public static void main(String[] args){
        // create a new account
        String message = "{userID: scudrt, password: 123456}";

        boolean result = UserAccountManager.getInstance().signup(message);
        if (result){
            System.out.println("signup");
            result = UserAccountManager.getInstance().login(message);
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
