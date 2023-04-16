package com.goncharenko.musiczoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class NewPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
    }

    public void inputNewPassword(View view) {
    }

    public void verifyPassword(View view) {
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, SendingCodeActivity.class);
        startActivity(intent);
    }
}
