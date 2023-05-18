package com.goncharenko.musiczoneapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class AudioModel implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("link")
    @Expose
    private String path;
    @SerializedName("name")
    @Expose
    private String title;
    @SerializedName("copyright")
    @Expose
    private String duration;

    @SerializedName("genre")
    @Expose
    private String genre;

    public AudioModel(String path, String title, String duration) {
        this.path = path;
        this.title = title;
        this.duration = duration;
    }

    public AudioModel(Integer id, String path, String title, String duration, String genre) {
        this.id = id;
        this.path = path;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
    }

    public AudioModel() {
        path = "";
        title = "";
        duration = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public void setAudioModel(AudioModel audioModel){
        path = audioModel.getPath();
        title = audioModel.getTitle();
        duration = audioModel.getDuration();
    }

    public boolean isEmpty(){
        return (path.equals("") || title.equals("") || duration.equals(""));
    }


    public boolean equals(AudioModel audioModel) {
        return path.equals(audioModel.path) && title.equals(audioModel.title) && duration.equals(audioModel.duration);
    }

}
