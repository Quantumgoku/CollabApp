package com.example.collabapp.model.dao;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    @Id
    private String id;

    private String title;
    private String content;

    private String ownerId;

    private List<String> listOfContributors;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    private int version;
}