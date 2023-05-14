package com.goncharenko.musiczoneapp.activities;

public interface MainListener {
    void onSignedIn(boolean isSignedIn);
    void setOnEmail(String email);
    void setOnPassword(String password);

    String getOnEmail();
    String getOnPassword();
}
