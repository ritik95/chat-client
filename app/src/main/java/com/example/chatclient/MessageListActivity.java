package com.example.chatclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatclient.config.MyUserDetails;
import com.example.chatclient.model.Message;
import com.example.chatclient.utils.ChatUtils;
import com.example.chatclient.adapter.MessageListAdapter;
import com.example.chatclient.config.ChatAppConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends AppCompatActivity {

    private Button buttonSend;
    private EditText messageText;
    private WebSocket webSocket;
    private List<Message> messageList;
    private RecyclerView messageRecycler;
    private MessageListAdapter messageListAdapter;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button buttonSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        buttonSend = findViewById(R.id.button_chatbox_send);
        messageText = findViewById(R.id.edittext_chatbox);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(MessageListActivity.this, PhoneLoginActivity.class);
            startActivity(intent);
            return;
        }
        String uName = currentUser.getDisplayName();
        MyUserDetails.getInstance().setUserName(uName);
        MyUserDetails.getInstance().setUserId(currentUser.getUid());
        final String userName = MyUserDetails.getInstance().getUserName();
        WebSocketFactory factory = new WebSocketFactory().setConnectionTimeout(5000);

        try {
            webSocket = factory.createSocket(ChatAppConfig.WEB_SOCKET_URL + userName);
            webSocket.addListener(new WebSocketAdapter(){
                @Override
                public void onTextMessage(WebSocket websocket, String message){
                    System.out.println("on text message "+ message);
                    Message message1 = ChatUtils.getMessage(message);
                    appendMessages(message1);
                }
            });
            webSocket.connectAsynchronously();
        } catch (IOException e) {
            e.printStackTrace();
        }


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageText.getText().length() == 0)
                    return;
                System.out.println("sending message : "+messageText.getText().toString());
                Message message = ChatUtils.createMessage(userName, messageText.getText().toString());
                sendMessage(messageText.getText().toString());
                appendMessages(message);
                messageText.setText("");
            }
        });

        messageList = new ArrayList<>();
        messageListAdapter = new MessageListAdapter(this, messageList);
        messageRecycler = findViewById(R.id.recylerview_message_list);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        messageRecycler.setAdapter(messageListAdapter);

    }

    public void sendMessage(String message) {
        if (webSocket.isOpen()) {
            webSocket.sendText(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (webSocket != null) {
            webSocket.disconnect();
            webSocket = null;
        }
    }

    private void appendMessages(final Message message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(message);
                messageListAdapter.notifyDataSetChanged();
                playBeep();
            }
        });
    }

    public void playBeep(){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
            ringtone.play();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
