package com.paperplane;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import static android.support.constraint.Constraints.TAG;

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
        new Thread(new Runnable(){
            @Override
            public void run() {
                while (true) {
                    if (isStop) {
                        break;
                    }

                    //接收代码
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    client = new SimpleClient();
                    try {
                        JSONObject json = new JSONObject();
                        json.put("MSGType", "ASK_MESSAGE");
                        json.put("userID", UserAccountClientManager.getInstance().getCurrentUser().getUserID());
                        client.send(json.toJSONString());
                        String msg = client.get();
                        if (msg != null) {
                            publishProgress(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).run();
        return true;
    }

    @Override
    protected void onProgressUpdate(String... values){
        Log.d(TAG, "onProgressUpdate: message received");
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
