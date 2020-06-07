package com.example.chatclient.utils;


import com.example.chatclient.constants.Constants;
import com.example.chatclient.model.Message;
import com.google.gson.Gson;

public class ChatUtils {
    private static final String NEW = "new";
    private static final String EXIT = "exit";
    private static final String MESSAGE = "message";

    public static Message getMessage(String msg){
        Message message = new Gson().fromJson(msg, Message.class);
        return message;
    }

    public static Message createMessage(String user, String msg){
        Message message = new Message();
        message.setFlag(Constants.MESSAGE);
        message.setCreatedAt(System.currentTimeMillis());
        message.setMessage(msg);
        Message.User sender = new Message.User();
        sender.setName(user);
        message.setSender(sender);
        return message;
    }
}
