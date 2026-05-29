package com.example.collabapp.services;

import com.example.collabapp.model.dao.RefreshToken;
import com.example.collabapp.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-expiry}")
    private long refreshTokenExpiry;

    public RefreshToken createRefreshToken(String userId,String deviceInfo){
        return refreshTokenRepository.save(RefreshToken.builder()
                        .token(UUID.randomUUID().toString())
                        .userId(userId)
                        .deviceInfo(deviceInfo)
                        .issuedAt(Instant.now())
                        .expiresAt(Instant.now().plusMillis(refreshTokenExpiry))
                        .revoked(false)
                .build());
    }

    public RefreshToken rotateRefreshToken(String oldTokenStr,String userId){
        RefreshToken oldToken=refreshTokenRepository.findByToken(oldTokenStr)
                .orElseThrow(()->new RuntimeException("Refresh Token not found"));

        if(oldToken.isRevoked()){
            revokeAllUserTokens(userId);
            throw new RuntimeException("Refresh token already used. Please login again");
        }
        if(oldToken.getExpiresAt().isBefore(Instant.now())){
            throw new RuntimeException("Refresh token expired. Please Login again");
        }
        //Rotate
        RefreshToken newToken=createRefreshToken(userId,oldToken.getDeviceInfo());
        oldToken.setRevoked(true);
        oldToken.setReplacedByToken(newToken.getToken());
        refreshTokenRepository.save(oldToken);
        return newToken;
    }

    public void revokeToken(String token){
        refreshTokenRepository.findByToken(token).ifPresent(rt->{
            rt.setRevoked(true);
            refreshTokenRepository.save(rt);
        });
    }

    public void revokeAllUserTokens(String userId){
        List<RefreshToken> tokens=refreshTokenRepository.findByUserIdAndRevokedFalse(userId);
        tokens.forEach(t->t.setRevoked(true));
        refreshTokenRepository.saveAll(tokens);
    }

}
