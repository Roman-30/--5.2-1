package com.goncharenko.musiczoneapp.service;

import android.util.Log;

import com.goncharenko.musiczoneapp.json.AudioInterface;
import com.goncharenko.musiczoneapp.models.JwtAccess;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AudioService {
    private static AudioService mInstance;
    private static final String OLD_BASE_URL = "http://217.25.225.200:8888";
    private static final String BASE_URL = "http://2.56.242.93:4080";
    private Retrofit mRetrofit;

    private String access = "";

    private AudioService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        JwtAccess jwtAccess = new JwtAccess();
        jwtAccess.setAccessToken(access);
        client.addInterceptor(jwtAccess);
        Log.d("Token", access);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    private AudioService(String access) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        JwtAccess jwtAccess = new JwtAccess();
        jwtAccess.setAccessToken(access);
        client.addInterceptor(jwtAccess);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static AudioService getInstance() {
        if (mInstance == null) {
            mInstance = new AudioService();
        }
        return mInstance;
    }

    public void setAccess(String access) {
        this.access = access;
        mInstance = new AudioService(access);
    }

    public AudioInterface getJSON() {
        return mRetrofit.create(AudioInterface.class);
    }
}
