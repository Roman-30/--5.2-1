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
}
