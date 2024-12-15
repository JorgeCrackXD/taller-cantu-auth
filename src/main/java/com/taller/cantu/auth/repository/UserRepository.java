package com.taller.cantu.auth.repository;

import com.taller.cantu.auth.dto.UserBlockedDTO;
import com.taller.cantu.auth.entity.User;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.AbstractJpaQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT * FROM users WHERE email = :email LIMIT 1", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE email = :email AND verified = false", nativeQuery = true)
    List<User> findByEmailAndNonVerified(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE email = :email AND verified = true", nativeQuery = true)
    Optional<User> findByEmailAndVerified(@Param("email") String email);

    @Query(value = "SELECT password FROM users WHERE email = :email LIMIT 1", nativeQuery = true)
    Optional<String> findPasswordByEmail(@Param("email") String email);

    @Query(value = "SELECT user_id, user_email, blocked FROM block_user_by_email(:email, :blocked)", nativeQuery = true)
    Map<String, Object> blockUser(@Param("email") String email, @Param("blocked") boolean blocked);
}
