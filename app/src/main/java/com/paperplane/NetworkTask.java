package com.paperplane;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkTask extends AsyncTask<Void, String, Boolean> {

    private NetworkListener listener;

    private boolean isStop = false;
    private boolean isSend = false;
    private String sendMSG="";

    private SimpleClient client;
    private ChatClientManager chatClientManager;

    public NetworkTask(NetworkListener listener){
        super();
        this.listener = listener;
        client = new SimpleClient();
        chatClientManager = ChatClientManager.getInstance();
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected Boolean doInBackground(Void... params){
        while(true) {
            Log.d("NetworkTask", "Looping");

            if(isStop){
                break;
            }
            if(isSend){
                //发送代码
                Log.d("NetworkTask","Send msg");
                client.send(sendMSG);
                isSend = false;
            }

            //接收代码
            try {
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            String msg = client.get();
            if(msg != null){
                    publishProgress(msg);
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
