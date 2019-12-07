package com.paperplane;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Queue;

public class NetworkSendTask extends AsyncTask<Void, Void, Boolean> {
    private NetworkListener listener;

    private SimpleClient client;

    private boolean isStop;

    private ArrayList<String> sendQueue;

    private ChatClientManager chatManager;

    public NetworkSendTask(NetworkListener listener){
        this.listener = listener;
        this.sendQueue = new ArrayList<String>();
        chatManager = ChatClientManager.getInstance();
        isStop = false;
    }

    @Override
    protected void onPreExecute(){
    }

    @Override
    protected Boolean doInBackground(Void... params){
        String sendMessage;
        while(!isStop){
            if(sendQueue.size()>0){
                sendMessage = sendQueue.remove(0);
                client = new SimpleClient();
                client.send(sendMessage);
                client.get();
            }
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Void... values){

    }

    @Override
    protected void onPostExecute(Boolean result){

    }

    public void stopSendTask(){
        isStop = true;
    }

    public void SendMessage(String msg){
        sendQueue.add(msg);
    }
}
