package com.example.collabapp.model.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public class NotesResponse {
    private List<NoteResponse> listOfNotes;
}
