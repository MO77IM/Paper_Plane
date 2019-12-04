//powered by SCUDRT
package com.drttest;

public class Server{
    public static void main(String[] args) {
        Thread t = new Thread(new SimpleServer(3000));
        t.start();
    }
}
