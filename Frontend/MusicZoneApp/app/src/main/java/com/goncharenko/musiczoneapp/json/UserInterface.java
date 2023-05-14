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
    @POST("/person/registration")
    Call<String> registration(@Body UserModel dto);
    @GET("/person/{id}")
    Call<UserModel> getUserFromId(@Path("id") int id);
    @POST("/person/entry{email}-{password}")
    Call<String> entry(@Path("email") String email, @Path("password") String password);
}
