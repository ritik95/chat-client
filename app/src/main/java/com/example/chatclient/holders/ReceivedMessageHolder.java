package com.example.chatclient.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatclient.model.Message;
import com.example.chatclient.utils.Utils;
import com.example.chatclient.R;

public class ReceivedMessageHolder extends RecyclerView.ViewHolder {

    private TextView messageText;
    private TextView timeText;
    private TextView nameText;
    private ImageView profileImage;

    public ReceivedMessageHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.received_message_body);
        timeText = itemView.findViewById(R.id.received_message_time);
        nameText = itemView.findViewById(R.id.text_message_name);
        profileImage = itemView.findViewById(R.id.image_message_profile);
    }

    public void bind(Message message, Context mContext){
        System.out.println("converted "+message);
        messageText.setText(message.getMessage());
        timeText.setText(Utils.getFormattedTime(message.getCreatedAt()));
        nameText.setText(message.getSender().getUserName());

       // Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
    }
}
