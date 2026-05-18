package com.example.collabapp.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UserResponse {
    private String id;
    private String userName;
    private String email;
}
