package com.goncharenko.musiczoneapp.service;

import com.goncharenko.musiczoneapp.json.AudioInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AudioService {
    private static AudioService mInstance;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private Retrofit mRetrofit;

    private AudioService() {
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

    public static AudioService getInstance() {
        if (mInstance == null) {
            mInstance = new AudioService();
        }
        return mInstance;
    }

    public AudioInterface getJSON() {
        return mRetrofit.create(AudioInterface.class);
    }
}
