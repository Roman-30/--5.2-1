package ru.vsu.cs.musiczoneserver.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private final String type = "Bearer";
    private String accessToken;

}
