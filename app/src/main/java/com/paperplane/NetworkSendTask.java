package com.paperplane;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Queue;

public class NetworkSendTask extends AsyncTask<String, Void, String> {
    private NetworkListener listener;

    private SimpleClient client;

    private ChatClientManager chatManager;

    public NetworkSendTask(NetworkListener listener){
        this.listener = listener;
        chatManager = ChatClientManager.getInstance();
    }

    @Override
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... params){
        String sendMessage = params[0];
        client = new SimpleClient();
        client.send(sendMessage);
        String res = client.get();
        return res;
    }

    @Override
    protected void onProgressUpdate(Void... values){

    }

    @Override
    protected void onPostExecute(String result){
        listener.onReceived(result);
    }
}
