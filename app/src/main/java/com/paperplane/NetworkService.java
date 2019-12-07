package com.paperplane;

import android.app.Service;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class NetworkService extends Service {

    private NetworkReceiveTask networkReceiveTask;
    private ChatClientManager chatClientManager;

    private NetworkBinder nBinder = new NetworkBinder();

    private NetworkListener receiveListener = new NetworkListener() {
        @Override
        public void onReceived(String msg) {
            JSONObject loader = JSONObject.parseObject(msg);
            String type = loader.getString("MSGType");
            if(type!=null){
                if(type.equals("SEND_TO")){//用户间发送消息
                    PrivateChat privateChat = chatClientManager.getChatByUserId(loader.getString("userID"));
                    if(privateChat == null){//当前聊天列表未创建与目标用户的聊天创建聊天窗口
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
                    chatClientManager.receiveTextMessage(privateChat, loader.getString("message"));
                }
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
        networkReceiveTask.execute();
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

        private String res;

        public String SendMessage(String msg){
            NetworkListener listener = new NetworkListener() {
                @Override
                public void onReceived(String content) {
                    res = content;
                }
            };
            NetworkSendTask networkSendTask = new NetworkSendTask(listener);
            networkSendTask.execute(msg);
            return res;
        }
    }
}
