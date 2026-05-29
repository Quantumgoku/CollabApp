package com.example.collabapp.model.dto;

import com.example.collabapp.model.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserResponse user;
}
