package com.taller.cantu.auth.service.impl;

import com.taller.cantu.auth.entity.User;
import com.taller.cantu.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.private.key.path}")
    private String privateKeyPath;

    @Value("${jwt.public.key.path}")
    private String publicKeyPath;

    @Value("${jwt.expiration}")
    private Long jwtExpirationInMs;

    private Key privateKey;
    private Key publicKey;


    @Override
    public String generateJwtToken(User user) throws Exception {
        privateKey = getPrivateKey();
        publicKey = getPublicKey();

        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getEmail());
    }

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public LocalDateTime getTokenExpirationDate(String token) throws Exception {
        publicKey = getPublicKey();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    private String createToken(Map<String, Object> claims, String subject) throws Exception {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    private PrivateKey getPrivateKey() throws Exception {
        ClassPathResource resource = new ClassPathResource(privateKeyPath);
        String privateKeyPEM = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

        // Eliminar el encabezado y pie de la llave PEM
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private PublicKey getPublicKey() throws Exception {
        ClassPathResource resource = new ClassPathResource(publicKeyPath);
        String publicKeyPEM = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

        // Eliminar el encabezado y pie de la llave PEM
        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

}
