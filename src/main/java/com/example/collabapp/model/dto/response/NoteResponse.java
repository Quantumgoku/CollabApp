package com.example.collabapp.model.dto.response;


import lombok.Builder;
import lombok.Setter;

@Builder
public class NoteResponse {
    private String id;
    private String title;
    private String content;
}
