package ru.vsu.cs.musiczoneserver.entity.jwt;

import lombok.Data;

@Data
public class RefreshJwtRequest {

    public String refreshToken;
}
