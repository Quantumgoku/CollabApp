package com.example.collabapp.model.dto.response;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {
    private String id;
    private String ownerId;
    private int version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String content;
}
