package com.paperplane;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatWindowAdapter extends RecyclerView.Adapter {
    private ArrayList<PrivateChat> dataList;

    public ChatWindowAdapter(ArrayList<PrivateChat> dataList){
        this.dataList = dataList;
    }

    public void setDataList(ArrayList<PrivateChat> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ChatWindowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        PrivateChat privateChat = dataList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView text;

        public ViewHolder(View view){
            super(view);
        }

        public void setData(Object object){
            PrivateChat privateChat = (PrivateChat)object;
            icon.setImageResource(privateChat.getTargetUser().getIcon());
            text.setText(privateChat.getMessages().get(privateChat.getMessages().size()-1).getContent());
        }
    }
}
