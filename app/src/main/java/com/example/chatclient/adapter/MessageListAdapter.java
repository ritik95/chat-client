package com.example.chatclient.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatclient.config.MyUserDetails;
import com.example.chatclient.constants.Constants;
import com.example.chatclient.holders.ReceivedMessageHolder;
import com.example.chatclient.model.Message;
import com.example.chatclient.R;
import com.example.chatclient.holders.LeftJoinConversationHolder;
import com.example.chatclient.holders.SentMessageHolder;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {

    private List<Message> messageList;
    private Context context;

    public MessageListAdapter(Context context, List<Message> messages){
        this.messageList = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == Constants.VIEW_TYPE_MESSAGE_SENT){

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);

        }else if(viewType == Constants.VIEW_TYPE_MESSAGE_RECEIVED){

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);

        } else if(viewType == Constants.VIEW_TYPE_COVERSATION_STATE_CHANGE){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.left_join_conversation, parent, false);
            return new LeftJoinConversationHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        switch (holder.getItemViewType()){
            case Constants.VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case Constants.VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message, context);
                break;
                default:
                    ((LeftJoinConversationHolder) holder).bind(message);
                    break;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position){
        Message message = messageList.get(position);
        //TODO : update this code for generic
        if(Constants.MESSAGE.equalsIgnoreCase(message.getFlag())){
            if(MyUserDetails.getInstance().getUserName().equalsIgnoreCase(message.getSender().getUserName())){
                return Constants.VIEW_TYPE_MESSAGE_SENT;
            }else {
                return Constants.VIEW_TYPE_MESSAGE_RECEIVED;
            }
        }else if(Constants.NEW.equalsIgnoreCase(message.getFlag()) || Constants.EXIT.equalsIgnoreCase(message.getFlag())){
            return Constants.VIEW_TYPE_COVERSATION_STATE_CHANGE;
        }
        return 0;
    }




}
