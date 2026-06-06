package com.example.collabapp.model.dao;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "note_history")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NoteHistory {
    @Id
    private String id;

    private String noteId;
    private String editedBy;
    private String title;
    private String content;
    private LocalDateTime editedAt;
    private int version;
}
