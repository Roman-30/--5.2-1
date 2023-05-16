package com.goncharenko.musiczoneapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.goncharenko.musiczoneapp.R;

public class AddMusicActivity extends AppCompatActivity {

    private EditText trackNameInput;
    private EditText authorInput;
    private EditText genreInput;

    private Button addMusicButton;
    private Button saveButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);

        trackNameInput = findViewById(R.id.track_name_input);
        authorInput = findViewById(R.id.author_input);
        genreInput = findViewById(R.id.genre_input);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> goBack());

        addMusicButton = findViewById(R.id.add_track_button);
        addMusicButton.setOnClickListener(v -> addMusic());

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> save());
    }

    public void goBack(){
        goToAccount();
    }

    public void save(){
        goToAccount();
    }

    public void addMusic(){

    }

    private void goToAccount(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragment", "Account");
        intent.putExtra("isSignIn", true);
        startActivity(intent);
    }
}