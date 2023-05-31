package com.goncharenko.musiczoneapp.json;

import com.goncharenko.musiczoneapp.models.AudioModel;
import com.goncharenko.musiczoneapp.models.UserModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AudioInterface {
    @GET("/music/get/all")
    Call<List<AudioModel>> getAllMusic();

    @GET("/playlist/musics/get/all")
    Call<List<AudioModel>> getSavedMusic(@Query("email") String email);

    @GET("/music/file")
    Call<ResponseBody> getMusicFile(@Query("link") String link);

    @POST("/playlist/song/add")
    Call<List<String>> addMusic(@Query("pl") String email, @Query("tr") Integer tr);

    @DELETE("/playlist/song/delete")
    Call<List<String>> deleteMusic(@Query("pl") String email, @Query("tr") Integer tr);

    @POST("/music/save")
    Call<List<String>> addNewMusic(@Body AudioModel dto);

    @DELETE("/music/file/delete")
    Call<List<String>> deleteNewMusic(@Query("id") Integer id);

    @POST("/music/file/add")
    Call<List<String>> loadMusic(@Query("name") String name);

    @PUT("/music/update")
    Call<List<String>> updateMusic(@Body AudioModel dto);


}
