package com.example.collabapp.model.dao;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String Id;

    private String username;
    private String email;
    private String hashedPassword;

    private long createdAt;
}
