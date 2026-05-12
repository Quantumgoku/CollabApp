package com.example.collabapp.model.dto.response;


import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {
    private String id;
    private String title;
    private String content;
}
