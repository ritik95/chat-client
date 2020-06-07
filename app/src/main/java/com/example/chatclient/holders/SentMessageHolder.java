package com.example.chatclient.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatclient.model.Message;
import com.example.chatclient.utils.Utils;
import com.example.chatclient.R;

public class SentMessageHolder extends RecyclerView.ViewHolder {

    public TextView messageText;
    public TextView timeText;

    public SentMessageHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.sent_message_body);
        timeText = itemView.findViewById(R.id.sent_message_time);
    }

    public void bind(Message message){
        messageText.setText(message.getMessage());
        timeText.setText(Utils.getFormattedTime(message.getCreatedAt()));
    }
}
