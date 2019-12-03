package com.paperplane;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tr.playplane.R;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private PrivateChat privateChat;
    private ArrayList<Message> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        Intent intent = getIntent();
        privateChat = (PrivateChat)getIntent().getSerializableExtra("privateChat");

        for (String mjson : privateChat.GetMessages()
            ) {
            dataList.add(new Message(mjson));
       }

       // RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
       // StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //recyclerView.setLayoutManager(layoutManager);
        //ChatAdapter adapter = new ChatAdapter();
       // adapter.replaceAll(dataList);
       // recyclerView.setAdapter(adapter);

    }

    private void LoadChat(PrivateChat chat){

    }

}
