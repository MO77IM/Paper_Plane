package com.paperplane;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

public class ChatListWindow extends AppCompatActivity {

    private ChatClientManager chatClientManager;

    private ChatListener listener;

    private RecyclerView recyclerView;
    private ChatWindowAdapter adapter;
    private LinearLayoutManager layoutManager;

    private Button addChatButton;//测试按钮
    private EditText userIdInput;


    NetworkService.NetworkBinder networkBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            networkBinder = (NetworkService.NetworkBinder) iBinder;
            //chatClientManager.setNetworkBinder(networkBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };


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

        Intent sIntent = new Intent(this, NetworkService.class);
        bindService(sIntent, connection, BIND_AUTO_CREATE);

        addChatButton = (Button)findViewById(R.id.add_chat_button);
        userIdInput = (EditText)findViewById(R.id.user_id_input);

        addChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = userIdInput.getText().toString();
                JSONObject json = new JSONObject();
                json.put("MSGType", "GET_USER");
                json.put("userId", userId);
                String userJson = networkBinder.SendMessage(json.toJSONString());
                if(userJson == null){
                    Toast.makeText(ChatListWindow.this,"未找到用户", Toast.LENGTH_SHORT).show();
                }
                json = JSONObject.parseObject(userJson);
                chatClientManager.startChat(new UserAccount(json));
            }
        });

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
