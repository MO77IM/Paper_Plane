package com.paperplane;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;
import com.paperplane.R;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private PrivateChat privateChat;
    private ArrayList<Message> dataList = new ArrayList<Message>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        Intent intent = getIntent();
        privateChat = (PrivateChat)getIntent().getSerializableExtra("privateChat");

        for (Message message : privateChat.GetMessages()
            ) {
            dataList.add(message);
       }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ChatAdapter adapter = new ChatAdapter();
        adapter.replaceAll(dataList);
        recyclerView.setAdapter(adapter);

    }

    private void LoadChat(PrivateChat chat){

    }

}
