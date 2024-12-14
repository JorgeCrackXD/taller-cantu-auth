package com.taller.cantu.auth.repository;

import com.taller.cantu.auth.entity.EmailVerificationToken;
import com.taller.cantu.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    @Query(value = "SELECT * FROM email_verification_token WHERE user_id = :user_id AND token = :token", nativeQuery = true)
    Optional<EmailVerificationToken> findByTokenAndUser(@Param("user_id") UUID userId, @Param("token") String token);
}
