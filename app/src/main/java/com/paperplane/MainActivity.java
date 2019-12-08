package com.paperplane;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private FriendListView explistview;
    private String[][] childernDate=new String[10][10];
    private String[] groupDate=new String[10];
    private int expendFlag=-1;
    private FriendHeaderAdapter adapter;
    /**
     * 测试用的活动
      */
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/**
 * 初始化
 */
        initView();
        initData();

/**
 * 启动网络服务
 */
        Intent intent = new Intent(this, NetworkService.class);
        startService(intent);


        UserAccount drt = new UserAccount("134", "456");
        drt.setNickname("drt");
        UserAccount mza = new UserAccount("78979","drtnb");
        mza.setNickname("mza");

        ChatClientManager chatClientManager = ChatClientManager.getInstance();
        chatClientManager.startChat(drt);
        chatClientManager.startChat(mza);

        PrivateChat drtChat = chatClientManager.getChatByUser(drt);
        if(drtChat != null){
            drtChat.AddMessage(new ChatWindowMessage(drt.getIcon(), "你好", "", ChatWindowMessage.RECEIVE));
        }

        Button button = (Button)findViewById(R.id.test_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ChatListWindow.class);
                startActivity(intent);
            }
        });

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initData() {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    /**
     *
     */
    private void initView(){

    }

}
