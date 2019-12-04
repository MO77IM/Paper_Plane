package com.paperplane;

public interface NetworkListener {

    void onReceived(String content);

    void onSend();
}
