package com.goncharenko.musiczoneapp.models;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class JwtAccess implements Interceptor {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private String accessToken;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Добавляем JWT токен в заголовок
        Request modifiedRequest = originalRequest.newBuilder()
                .header(HEADER_AUTHORIZATION, TOKEN_PREFIX + accessToken)
                .build();

        return chain.proceed(modifiedRequest);
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
