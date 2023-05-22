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
    Call<String> addMusic(@Query("pl") String email, @Query("tr") Integer tr);

    @DELETE("/playlist/song/delete")
    Call<String> deleteMusic(@Query("pl") String email, @Query("tr") Integer tr);

}
