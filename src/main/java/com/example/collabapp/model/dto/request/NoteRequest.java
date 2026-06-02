package com.example.collabapp.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteRequest {
    @NotBlank(message = "Title cannot be empty")
    @Size(max=100, message = "Title cannot exceed 100 chars")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    private int version;
}
