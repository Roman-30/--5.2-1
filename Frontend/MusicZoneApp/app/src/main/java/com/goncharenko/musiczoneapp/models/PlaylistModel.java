package com.goncharenko.musiczoneapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaylistModel {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("duration")
    @Expose
    private String duration;

    public PlaylistModel(String title, String duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
