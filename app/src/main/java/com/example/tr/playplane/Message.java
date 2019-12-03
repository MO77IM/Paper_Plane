package com.example.tr.playplane;

import java.io.Serializable;

public class Message implements Serializable {
    public static final int SEND = 0;
    public static final int RECEIVE = 1;

    private int icon = R.mipmap.ic_launcher;
    private String content = "";
    private String image = "";
    private int type = SEND;

    public Message(String json){
        SimpleJSON resolver = new SimpleJSON(json);

        icon = Integer.parseInt(resolver.getValue("icon"));
        content = resolver.getValue("content");
        image = resolver.getValue("image");
        type = resolver.getValue("type")=="SEND" ? 0 : 1;
    }

    public Message(int icon, String content, String image, int type){
        this.icon = icon;
        this.content = content;
        this.image = image;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getIcon() {
        return icon;
    }

    public String getImage() {
        return image;
    }

    public int getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toJSON(){
        String json = "{ ";
        json += "type:" + (type == 1 ? "SEND" : "RECEIVE") + "," ;
        json += "icon:" + icon + ",";
        json += "content:" + content + ",";
        json += "image" + image;
        json +=  "}";
        return json;
    }
}
