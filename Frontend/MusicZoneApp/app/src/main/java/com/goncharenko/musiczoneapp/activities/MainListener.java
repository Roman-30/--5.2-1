package com.goncharenko.musiczoneapp.activities;

import com.goncharenko.musiczoneapp.models.AudioModel;

import java.util.ArrayList;
import java.util.List;

public interface MainListener {
    void onSignedIn(boolean isSignedIn);
    boolean getOnSignedIn();
    void setOnEmail(String email);
    void setOnPassword(String password);

    void setOnAudioModel(AudioModel audioModel);
    void setOnAudioModel(List<AudioModel> audioModel);

    String getOnEmail();
    String getOnPassword();
    ArrayList<AudioModel> getOnAudioModels();

    boolean isAdmin();
    void setAdmin(boolean isAdmin);
}
