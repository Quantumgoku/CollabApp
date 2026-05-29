package com.example.collabapp.model.dao;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "refresh_tokens")
@Data
@Builder
public class RefreshToken {
    @Id
    private String id;

    @Indexed(unique = true)
    private String token;

    private String userId;
    private String deviceInfo;
    private Instant issuedAt;
    private Instant expiresAt;

    private boolean revoked=false;
    private String replacedByToken;
}
