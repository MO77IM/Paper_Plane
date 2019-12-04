package com.paperplane;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

public class NetworkTask extends AsyncTask<Void, String, Boolean> {

    private NetworkListener listener;

    private boolean isStop = false;
    private boolean isSend = false;
    private SimpleClient client;
    private String sendMSG="";

    private ChatManager chatManager;

    public NetworkTask(NetworkListener listener){
        super();
        this.listener = listener;
        chatManager = ChatManager.getInstance();
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected Boolean doInBackground(Void... params){
        while(true) {
            //Log.d("NetworkTask", "Looping");

            if(isStop){
                break;
            }
            if(isSend){
                //发送代码
                Log.d("NetworkTask","Send msg");
                client = new SimpleClient();
                client.send(sendMSG);
                client.get();
                isSend = false;
            }

            //接收代码
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            client = new SimpleClient();
            try {
                JSONObject json = new JSONObject();
                json.put("MSGType", "RECEIVE_FOR");
                json.put("UserID", UserAccountClientManager.getInstance().getCurrentUser().getUserID());
               client.send(json.toJSONString());
                String msg = client.get();
            if(msg != null){
                    publishProgress(msg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(String... values){
        String content = values[0];
        listener.onReceived(content);
    }

    @Override
    protected void onPostExecute(Boolean result){

    }

    public void StopNetwork(){
        isStop = true;
    }

    public void SendMessage(String msg){
        isSend = true;
        sendMSG = msg;
    }
}
