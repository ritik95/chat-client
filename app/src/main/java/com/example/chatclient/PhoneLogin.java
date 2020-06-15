package com.example.chatclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.TimeUnit;

public class PhoneLogin extends AppCompatActivity {

    private EditText phoneNumber;
    private EditText userName;
    private Button buttonJoin;
    private Button requestOtp;
    private FirebaseAuth mAuth;
    private EditText verificationCode;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        phoneNumber = findViewById(R.id.phone_number);
        userName = findViewById(R.id.user_name);
        buttonJoin = findViewById(R.id.button_join);
        requestOtp = findViewById(R.id.request_otp);
        verificationCode = findViewById(R.id.verification_code);

        mAuth = FirebaseAuth.getInstance();

        requestOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pNumber = phoneNumber.getText().toString().trim();
                if(pNumber.isEmpty() || pNumber.length() < 10){
                    phoneNumber.setError("Enter a valid mobile number!");
                    phoneNumber.requestFocus();
                    return;
                }
                sendVerificationCode(pNumber);
            }
        });

        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = verificationCode.getText().toString();
                if(otp.isEmpty() || otp.length() < 6){
                    verificationCode.setError("Invalid OTP!");
                    verificationCode.requestFocus();
                    return;
                }
                verifyVerificationCode(otp);
            }
        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verificationCode.setText(code);
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(PhoneLogin.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            //mResendToken = forceResendingToken;
        }
    };

    private void verifyVerificationCode(String otp){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(PhoneLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            updateUserDetails(userName.getText().toString());
                            Intent intent = new Intent(PhoneLogin.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            String message = "Something is wrong, we will fix it soon...";
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(PhoneLogin.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String mobileNumber){
        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber("+91" + mobileNumber, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallbacks);
    }

    private void updateUserDetails(String userName){
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build();

        mUser.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                        }
                    }
                });
    }

}
