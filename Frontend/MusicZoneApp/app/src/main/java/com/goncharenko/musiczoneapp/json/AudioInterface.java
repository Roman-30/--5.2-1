package com.goncharenko.musiczoneapp.json;

import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AudioInterface {
    @GET("/music/get/all")
    Call<List<AudioModel>> getAllMusic();

    @GET("/music/file")
    Call<byte[]> getMusicFile();

}
