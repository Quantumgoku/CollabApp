package com.example.collabapp.services;

import com.example.collabapp.model.dao.RefreshToken;
import com.example.collabapp.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

// services/RefreshTokenCleanupService.java
@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenCleanupService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanupExpiredTokens() {
        Instant cutoff = Instant.now().minus(7, ChronoUnit.DAYS);

        List<RefreshToken> expired = refreshTokenRepository
                .findByExpiresAtBefore(Instant.now());

        List<RefreshToken> oldRevoked = refreshTokenRepository
                .findByRevokedTrueAndIssuedAtBefore(cutoff);

        refreshTokenRepository.deleteAll(expired);
        refreshTokenRepository.deleteAll(oldRevoked);

        log.info("Cleaned up {} expired and {} old revoked tokens",
                expired.size(), oldRevoked.size());
    }
}