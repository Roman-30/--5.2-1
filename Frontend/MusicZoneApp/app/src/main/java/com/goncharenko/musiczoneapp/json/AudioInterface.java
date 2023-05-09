package com.goncharenko.musiczoneapp.json;

import com.goncharenko.musiczoneapp.models.AudioModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AudioInterface {
    @GET("/audio/{id}")
    public Call<AudioModel> getPostWithID(@Path("id") int id);

    @GET("/audio")
    public Call<List<AudioModel>> getAllPosts();

    @GET("/audio")
    public Call<List<AudioModel>> getPostOfUser(@Query("userId") int id);

    @POST("/audio")
    public Call<AudioModel> postData(@Body AudioModel data);
}
