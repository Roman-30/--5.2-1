package com.goncharenko.musiczoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SendingCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_code);
    }

    public void sendCode(View view) {
    }

    public void verifyCode(View view) {
        Intent intent = new Intent(this, NewPasswordActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
    }
}
