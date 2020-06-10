package com.example.chatclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatclient.config.MyUserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NameActivity extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPassword;
    private Button buttonLogin;
    private Button buttonSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        buttonLogin = findViewById(R.id.login);
        userEmail = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        buttonSignUp = findViewById(R.id.sign_up);
        mAuth = FirebaseAuth.getInstance();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(userEmail.getText().toString(), userPassword.getText().toString());
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(userEmail.getText().toString(), userPassword.getText().toString());
            }
        });


    }

    private void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           String name = userEmail.getText().toString().trim();
                           Intent intent = new Intent(NameActivity.this, MessageListActivity.class);
                           MyUserDetails myUserDetails = MyUserDetails.getInstance();
                           myUserDetails.setUserName(name);
                           startActivity(intent);
                           userEmail.setText("");
                           userPassword.setText("");
                       }else {
                           Toast.makeText(NameActivity.this, "Authentication failed.",
                                   Toast.LENGTH_SHORT).show();
                       }

                    }
                });
    }

    private void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String name = userEmail.getText().toString().trim();
                            Intent intent = new Intent(NameActivity.this, MessageListActivity.class);
                            MyUserDetails myUserDetails = MyUserDetails.getInstance();
                            myUserDetails.setUserName(name);
                            startActivity(intent);
                            userEmail.setText("");
                            userPassword.setText("");
                        }else {
                            Toast.makeText(NameActivity.this, "Login Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
