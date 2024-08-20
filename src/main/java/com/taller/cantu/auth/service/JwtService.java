package com.taller.cantu.auth.service;

import com.taller.cantu.auth.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface JwtService {

    public String generateJwtToken(User user) throws Exception;

    public LocalDateTime getTokenExpirationDate(String token) throws Exception;

    public Claims extractClaims(String token);
}
