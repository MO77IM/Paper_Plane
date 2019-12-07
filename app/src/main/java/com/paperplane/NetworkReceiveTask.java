package com.paperplane;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

public class NetworkReceiveTask extends AsyncTask<Void, String, Boolean> {

    private NetworkListener listener;

    private boolean isStop = false;
    private SimpleClient client;

    private ChatClientManager chatClientManager;


    public NetworkReceiveTask(NetworkListener listener){
        super();
        this.listener = listener;

        chatClientManager = ChatClientManager.getInstance();
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected Boolean doInBackground(Void... params){
        while(true) {
            Log.d("NetworkReceiveTask", "Looping");

            if(isStop){
                break;
            }

            //接收代码
            try {
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            client = new SimpleClient();
            try {
                JSONObject json = new JSONObject();
                json.put("MSGType", "ASK_MESSAGE");
                json.put("userID", UserAccountClientManager.getInstance().getCurrentUser().getUserID());
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
}
