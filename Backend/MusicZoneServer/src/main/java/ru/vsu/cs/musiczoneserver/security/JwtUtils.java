package ru.vsu.cs.musiczoneserver.security;

import io.jsonwebtoken.Claims;
import ru.vsu.cs.musiczoneserver.entity.model.ModelAuthentication;
import ru.vsu.cs.musiczoneserver.entity.model.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUtils {
    public static ModelAuthentication generate(Claims claims) {
        final ModelAuthentication jwtInfoToken = new ModelAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setFirstName(claims.get("email", String.class));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
