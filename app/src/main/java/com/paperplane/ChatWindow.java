package com.paperplane;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.paperplane.R;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private PrivateChat privateChat;
    Button sendButton;
    EditText input;
    RecyclerView recyclerView;
    ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        final Intent intent = getIntent();
        privateChat = (PrivateChat)getIntent().getSerializableExtra("privateChat");


        //初始化recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.message_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(privateChat.GetMessages());
        recyclerView.setAdapter(adapter);


        input = (EditText)findViewById(R.id.text_input);
        sendButton = (Button)findViewById(R.id.tvSend);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Message message = new Message(R.mipmap.ic_launcher, input.getText().toString(), "", Message.SEND);
                privateChat.SendMessage(message);
                Log.d("Chat Window", "onClick: send button clicked");
                adapter.notifyItemInserted(privateChat.GetMessages().size() - 1);
                recyclerView.scrollToPosition(privateChat.GetMessages().size() - 1);
                input.setText("");
            }
        });


    }

    private void LoadChat(PrivateChat chat){

    }

}
