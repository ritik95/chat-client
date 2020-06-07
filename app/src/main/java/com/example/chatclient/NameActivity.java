package com.example.chatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatclient.config.MyUserDetails;
import com.example.chatclient.R;

public class NameActivity extends AppCompatActivity {

    private EditText enterName;
    private Button buttonJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        buttonJoin = findViewById(R.id.btnjoin);
        enterName = findViewById(R.id.name);

        //getActionBar().hide();

        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = enterName.getText().toString().trim();
                Intent intent = new Intent(NameActivity.this, MessageListActivity.class);
                MyUserDetails myUserDetails = MyUserDetails.getInstance();
                myUserDetails.setUserName(name);
                startActivity(intent);
                enterName.setText("");
            }
        });
    }
}
