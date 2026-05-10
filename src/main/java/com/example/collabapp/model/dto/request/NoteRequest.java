package com.example.collabapp.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoteRequest {
    @NotBlank
    @Size(max=100)
    private String title;

    @NotBlank
    private String content;
}
