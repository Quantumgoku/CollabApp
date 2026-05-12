package com.example.collabapp.model.dao;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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

    private List<String> listOfNotes;

    private long createdAt;

}
