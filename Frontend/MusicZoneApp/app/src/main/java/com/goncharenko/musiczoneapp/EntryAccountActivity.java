package com.goncharenko.musiczoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class EntryAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }

    public void goBack(View view) {
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}
