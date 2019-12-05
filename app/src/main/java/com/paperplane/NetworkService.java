package com.paperplane;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

public class NetworkService extends Service {

    private NetworkTask networkTask;
    private ChatClientManager chatClientManager;

    private NetworkBinder nBinder = new NetworkBinder();

    private NetworkListener listener = new NetworkListener() {
        @Override
        public void onReceived(String msg) {
            JSONObject loader = JSONObject.parseObject(msg);
            String type = loader.getString("MSGType");
            if(type!=null){
                if(type.equals("SEND_TO")){//用户间发送消息
                    PrivateChat privateChat = chatClientManager.getChatByUserId(loader.getString("UserID"));
                    if(privateChat == null){//当前聊天列表未创建与目标用户的聊天，在好友中搜索该用户
                        //代码
                    }
                    privateChat.ReceiveTextMessage(loader.getString("content"));
                }
            }
        }

        @Override
        public void onSend(){

        }
    };

    public NetworkService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();

        chatClientManager = ChatClientManager.getInstance();
        networkTask = new NetworkTask(listener);
        networkTask.execute();
        Log.d("NetworkService", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("NetworkService", "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return nBinder;
    }

    class NetworkBinder extends Binder{

        public void SendMessage(String msg){
            networkTask.SendMessage(msg);
        }
    }
}
