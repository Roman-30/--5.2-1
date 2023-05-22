package ru.vsu.cs.musiczoneserver.entity.jwt;

import lombok.Data;

@Data
public class JwtRequest {

    private String email;
    private String password;
}