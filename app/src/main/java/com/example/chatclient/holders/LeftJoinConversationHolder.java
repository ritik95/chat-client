package com.example.chatclient.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatclient.model.Message;
import com.example.chatclient.R;

public class LeftJoinConversationHolder extends RecyclerView.ViewHolder {
    private TextView conversationStateText;

    public LeftJoinConversationHolder(@NonNull View itemView) {
        super(itemView);
        conversationStateText = itemView.findViewById(R.id.left_join_conversation);
    }

    public void bind(Message message){
        conversationStateText.setText(message.getMessage());
    }
}
