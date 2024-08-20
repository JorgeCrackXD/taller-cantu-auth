package com.taller.cantu.auth.service.impl;

import com.taller.cantu.auth.entity.EmailVerificationToken;
import com.taller.cantu.auth.entity.User;
import com.taller.cantu.auth.repository.EmailVerificationTokenRepository;
import com.taller.cantu.auth.service.EmailVerificationTokenService;
import com.taller.cantu.auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public String generateEmailVerificationToken(User user) throws Exception {

        // Generar el token
        String token = jwtService.generateJwtToken(user);
        LocalDateTime expirationDate = jwtService.getTokenExpirationDate(token);

        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(token);
        emailVerificationToken.setUser(user);
        emailVerificationToken.setExpiration_datetime(expirationDate);

        emailVerificationTokenRepository.save(emailVerificationToken);

        return token;
    }
}
