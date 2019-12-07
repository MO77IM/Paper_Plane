package com.paperplane;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ChatListWindow extends AppCompatActivity {

    private ChatClientManager chatClientManager;

    private ChatListener listener;

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

        listener = new ChatListener() {
            @Override
            public void OnRefresh() {
                adapter.notifyDataSetChanged();
                adapter.notifyItemInserted(chatClientManager.getChatSize() - 1);
                recyclerView.scrollToPosition(chatClientManager.getChatSize() - 1);
            }
        };

        chatClientManager.setChatListListener(listener);
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.notifyDataSetChanged();
        adapter.notifyItemInserted(chatClientManager.getChatSize() - 1);
        recyclerView.scrollToPosition(chatClientManager.getChatSize() - 1);
    }
}
