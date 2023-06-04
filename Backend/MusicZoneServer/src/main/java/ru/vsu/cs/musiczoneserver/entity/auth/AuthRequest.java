package ru.vsu.cs.musiczoneserver.entity.auth;

import lombok.Data;

@Data
public class AuthRequest {

    private String email;
    private String password;
}