package com.goncharenko.musiczoneapp.json;

import com.goncharenko.musiczoneapp.models.JwtRequest;
import com.goncharenko.musiczoneapp.models.JwtResponse;
import com.goncharenko.musiczoneapp.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserInterface {
    @POST("/person/registration")
    Call<List<String>> registration(@Body UserModel dto);
    ///get/email
    @GET("/person/get/user")
    Call<UserModel> getUserByEmail(@Query("email") String email);

    @POST("/person/login")
    Call<JwtResponse> login(@Body JwtRequest request);
    @GET("/person/send/{email}")
    Call<List<String>> sendCode(@Path("email") String email, @Query("code") String code);

    @GET("/person/{id}")
    Call<UserModel> getUserFromId(@Path("id") int id);

    @PUT("/person/update")
    Call<List<String>> userUpdate(@Body UserModel dto);
    @PUT("/person/pass/{id}")
    Call<List<String>> changePassword(@Path("id") int id, @Query("pass") String password);
}
