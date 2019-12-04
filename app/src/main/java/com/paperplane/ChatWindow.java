package com.paperplane;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
    LinearLayoutManager layoutManager;
    ChatManager chatManager;

    private NetworkService.NetworkBinder networkBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            networkBinder = (NetworkService.NetworkBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatManager = ChatManager.getInstance();

        final Intent intent = getIntent();
        privateChat = chatManager.getChatByPosition(intent.getIntExtra("privateChat", -1));


        //初始化recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.message_list_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(privateChat.getMessages());
        recyclerView.setAdapter(adapter);

        Intent sIntent = new Intent(this, NetworkService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        chatManager.setNetworkBinder(networkBinder);

        input = (EditText)findViewById(R.id.text_input);
        sendButton = (Button)findViewById(R.id.tvSend);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                chatManager.sendTextMessageInChat(privateChat, input.getText().toString());
                adapter.notifyItemInserted(privateChat.getMessages().size() - 1);
                recyclerView.scrollToPosition(privateChat.getMessages().size() - 1);
                input.setText("");
            }
        });


    }
}
