package ru.vsu.cs.musiczoneserver.entity.jwt;

import lombok.Data;

@Data
public class JwtRequest {

    private String username;
    private String password;
}