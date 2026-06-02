package com.example.collabapp.repository;

import com.example.collabapp.model.dao.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken,String> {
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findByUserId(String userId);
    List<RefreshToken> findByUserIdAndRevokedFalse(String userId);
    void deleteByUserId(String userId);
    List<RefreshToken> findByExpiresAtBefore(Instant now);
    List<RefreshToken> findByRevokedTrueAndIssuedAtBefore(Instant cutoff);
}
