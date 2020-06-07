package com.example.chatclient.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getFormattedTime(Long timestamp){
        Date date = new Date(timestamp);
        DateFormat format = new SimpleDateFormat("HH:mm");
        String formatted = format.format(date);
        return formatted;
    }
}
