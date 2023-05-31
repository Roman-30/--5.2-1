package ru.vsu.cs.musiczoneserver.service;

import io.jsonwebtoken.Claims;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.vsu.cs.musiczoneserver.entity.Person;
import ru.vsu.cs.musiczoneserver.entity.jwt.JwtResponse;
import ru.vsu.cs.musiczoneserver.repository.PersonRepository;
import ru.vsu.cs.musiczoneserver.service.jwtcomponent.JwtProvider;

import javax.security.auth.message.AuthException;

@Service
public class TokenService {
    private final PersonRepository personRepository;
    private final JwtProvider jwtProvider;

    public TokenService(PersonRepository personRepository, JwtProvider jwtProvider) {
        this.personRepository = personRepository;
        this.jwtProvider = jwtProvider;
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();

            final Person user = personRepository.findByEmail(username).orElseThrow();
            final String accessToken = jwtProvider.generateAccessToken(user);
            return new JwtResponse(accessToken, null);
        }
        throw new AuthException("Invalid refresh token");
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();

            final Person person = personRepository.findByEmail(username).orElseThrow();
            final String accessToken = jwtProvider.generateAccessToken(person);
            final String newRefreshToken = jwtProvider.generateRefreshToken(person);
            return new JwtResponse(accessToken, newRefreshToken);

        }
        throw new AuthException("Invalid JWT token");
    }
}
