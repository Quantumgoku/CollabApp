package com.example.collabapp.services;

import com.example.collabapp.model.dto.request.NoteRequest;
import com.example.collabapp.model.dto.response.NoteResponse;
import com.example.collabapp.model.dto.response.NotesResponse;

import java.util.Optional;

public interface NoteService {
    NoteResponse saveNote(NoteRequest request);
    NoteResponse getNote(String id);
    NotesResponse fetchNotes();
    Optional<NoteResponse> updateNote(NoteRequest note, String noteId);
    void deleteNote(String noteId);
}
