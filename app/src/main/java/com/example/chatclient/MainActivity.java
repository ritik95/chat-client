package com.example.chatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatclient.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(MainActivity.this, PhoneLogin.class);
            startActivity(intent);
            return;
        }

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
