package com.paperplane;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ChatListWindow extends AppCompatActivity {

    private ChatClientManager chatClientManager;

    private RecyclerView recyclerView;
    private ChatWindowAdapter adapter;
    private LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatClientManager = ChatClientManager.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.chat_list_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatWindowAdapter(this, chatClientManager.getChatList());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.notifyDataSetChanged();
    }
}
