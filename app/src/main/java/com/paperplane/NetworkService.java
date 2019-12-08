package com.paperplane;

import android.app.Service;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.support.constraint.Constraints.TAG;

public class NetworkService extends Service {

    private NetworkReceiveTask networkReceiveTask;
    private ChatClientManager chatClientManager;

    private NetworkBinder nBinder = new NetworkBinder();

    ExecutorService executors = Executors.newCachedThreadPool();

    private NetworkListener receiveListener = new NetworkListener() {
        @Override
        public void onReceived(String msg) {
            JSONObject loader = JSONObject.parseObject(msg);
            for (int i = 0; i < loader.getIntValue("size"); i++) {

                ChatMessage chatMessage = new ChatMessage(loader.getJSONObject("message" + i));

                PrivateChat privateChat = chatClientManager.getChatByUserId(chatMessage.getSenderID());
                if (privateChat == null) {//当前聊天列表未创建与目标用户的聊天创建聊天窗口
                    SimpleClient client = new SimpleClient();
                    JSONObject json = new JSONObject();
                    json.put("MSGType", "GET_USER");
                    json.put("userID", loader.getString("userID"));
                    client.send(json.toJSONString());
                    String userStr = client.get();
                    json = JSONObject.parseObject(userStr);
                    UserAccount user = new UserAccount(json);
                    chatClientManager.startChat(user);
                    privateChat = chatClientManager.getChatByUser(user);
                }
                chatClientManager.receiveTextMessage(privateChat, chatMessage.getMessage());
            }
        }
    };

    public NetworkService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();

        chatClientManager = ChatClientManager.getInstance();
        networkReceiveTask = new NetworkReceiveTask(receiveListener);
        networkReceiveTask.executeOnExecutor(executors);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return nBinder;
    }

    class NetworkBinder extends Binder{

        private String res = null;

        public void SendMessage(String msg, NetworkListener listener){
            Log.d(TAG, "SendMessage: "+res);
            NetworkSendTask networkSendTask = new NetworkSendTask(listener);
            networkSendTask.execute(msg);
        }

        public void SendMessage(String msg){
            NetworkListener listener = new NetworkListener() {
                @Override
                public void onReceived(String content) {

                }
            };
            NetworkSendTask networkSendTask = new NetworkSendTask(listener);
            networkSendTask.execute(msg);
        }

        public String getRes(){
            return res;
        }
    }
}
