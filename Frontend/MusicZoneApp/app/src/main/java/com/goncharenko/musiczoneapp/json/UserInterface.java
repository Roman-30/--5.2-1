package com.goncharenko.musiczoneapp.json;

import com.goncharenko.musiczoneapp.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserInterface {
    @GET("/user/{id}")
    public Call<UserModel> getPostWithID(@Path("id") int id);

    @GET("/user")
    public Call<List<UserModel>> getAllPosts();

    @GET("/user")
    public Call<List<UserModel>> getPostOfUser(@Query("userId") int id);

    @POST("/user")
    public Call<UserModel> postData(@Body UserModel data);
}
