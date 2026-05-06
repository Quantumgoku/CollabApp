package com.example.collabapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private long createdAt;
    private long updateAt;

    private int version;
}