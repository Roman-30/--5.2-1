package com.goncharenko.musiczoneapp.json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MailInterface {
    @GET("/person/send/{email}")
    Call<List<String>> sendCode(@Path("email") String email, @Query("code") String code);
}
