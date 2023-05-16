package com.goncharenko.musiczoneapp.activities;

import com.goncharenko.musiczoneapp.models.AudioModel;

import java.util.ArrayList;

public interface MainListener {
    void onSignedIn(boolean isSignedIn);
    void setOnEmail(String email);
    void setOnPassword(String password);

    void setOnAudioModel(AudioModel audioModel);

    String getOnEmail();
    String getOnPassword();

    ArrayList<AudioModel> getOnAudioModels();
}
