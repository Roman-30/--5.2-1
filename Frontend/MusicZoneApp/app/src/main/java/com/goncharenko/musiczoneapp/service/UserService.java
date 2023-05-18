package com.goncharenko.musiczoneapp.service;

import com.goncharenko.musiczoneapp.json.UserInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private static UserService mInstance;
    private static final String BASE_URL = "http://217.25.225.200:8888";
    private Retrofit mRetrofit;

    private UserService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static UserService getInstance() {
        if (mInstance == null) {
            mInstance = new UserService();
        }
        return mInstance;
    }

    public UserInterface getJSON() {
        return mRetrofit.create(UserInterface.class);
    }
}
