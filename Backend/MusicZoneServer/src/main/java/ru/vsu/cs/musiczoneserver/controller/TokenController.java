package ru.vsu.cs.musiczoneserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.musiczoneserver.entity.jwt.JwtResponse;
import ru.vsu.cs.musiczoneserver.entity.jwt.RefreshJwtRequest;
import ru.vsu.cs.musiczoneserver.service.TokenService;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("/auth")
public class TokenController {
    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = tokenService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = tokenService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
