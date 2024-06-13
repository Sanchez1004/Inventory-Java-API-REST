package com.cesar.apirest.apirest.user.jwt;

import com.cesar.apirest.apirest.user.entity.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.function.Function;

public interface JwtService {
    String getToken(UserEntity user);
    String getEmailFromToken(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    <T> T getClaim(String token, Function<Claims, T> claimsResolver);
}
