package com.example.collabapp.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoteHistoryResponse {
    private String id;
    private String noteId;
    private String editedBy;
    private String title;
    private String content;
    private int version;
    private LocalDateTime editedAt;
}
