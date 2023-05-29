package com.goncharenko.musiczoneapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.service.AudioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMusicActivity extends AppCompatActivity {

    private EditText trackNameInput;
    private EditText authorInput;
    private EditText genreInput;

    private Button addMusicButton;
    private Button saveButton;
    private Button backButton;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            email = extras.getString("email");
        }

        trackNameInput = findViewById(R.id.track_name_input);
        authorInput = findViewById(R.id.author_input);
        genreInput = findViewById(R.id.genre_input);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> goBack());

        addMusicButton = findViewById(R.id.add_track_button);
        addMusicButton.setOnClickListener(v -> addMusic(v));

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> save(v));
    }

    public void goBack(){
        goToAccount();
    }

    public void save(View view){
        String trackName = trackNameInput.getText().toString().trim();
        String author = authorInput.getText().toString().trim();
        String genre = genreInput.getText().toString().trim();


        AudioModel dto = new AudioModel(0, "", trackName, author, genre);
        AudioService
                .getInstance()
                .getJSON()
                .addNewMusic(dto)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(view.getContext(),
                                    "Трек добавился",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(view.getContext(),
                                "Ошибка при добавлении",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        goToAccount();
    }

    public void addMusic(View view){
    }

    private void goToAccount(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragment", "Account");
        intent.putExtra("isSignIn", true);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}