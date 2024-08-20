package com.taller.cantu.auth.service;

import com.taller.cantu.auth.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface EmailVerificationTokenService {

    public String generateEmailVerificationToken(User user) throws Exception;
}
