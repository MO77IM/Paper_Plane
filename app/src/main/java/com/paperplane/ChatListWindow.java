package com.paperplane;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChatListWindow extends AppCompatActivity {

    private ChatManager chatManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatManager = ChatManager.getInstance();


    }
}
