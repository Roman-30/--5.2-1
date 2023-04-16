package com.goncharenko.musiczoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    public void goHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goPlayer(View view){
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }

    public void goAccount(View view){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void editAccount(View view) {
        Intent intent = new Intent(this, EditAccountActivity.class);
        startActivity(intent);
    }

    public void singOut(View view) {
    }

    public void checkMusic(View view) {
        Intent intent = new Intent(this, MyMusicActivity.class);
        startActivity(intent);
    }

    public void addMusic(View view) {
    }
}
