package com.goncharenko.musiczoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MyMusicActivity extends AppCompatActivity {

    private String[] music = new String[]{"music_1", "music_2", "music_3", "music_4", "music_5"};
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_music);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.music_item, R.id.music_name, music);
        listView.setAdapter(adapter);
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


    public void searchMusic(View view) {
    }
}
