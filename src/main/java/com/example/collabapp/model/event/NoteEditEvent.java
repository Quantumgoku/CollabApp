package com.example.collabapp.model.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteEditEvent {
    private String noteId;
    private String editedBy;
    private String editedByName;
    private String title;
    private String content;
    private int version;
    private LocalDateTime editedAt;

}
