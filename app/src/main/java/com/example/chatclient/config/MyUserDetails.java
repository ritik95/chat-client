package com.example.chatclient.config;


import lombok.Data;

@Data
public class MyUserDetails {

    private static MyUserDetails instance;
    private String userName;
    private String userId;

    public static MyUserDetails getInstance(){
        if (null != instance) return instance;
        synchronized (MyUserDetails.class) {
            if (null != instance) return instance;

            instance = new MyUserDetails();
        }
        return instance;
    }

    private MyUserDetails(){}

}
