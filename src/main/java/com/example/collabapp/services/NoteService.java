package com.example.collabapp.services;

import com.example.collabapp.model.dto.request.NoteRequest;
import com.example.collabapp.model.dto.response.NoteResponse;

import java.util.List;

public interface NoteService {
    NoteResponse saveNote(NoteRequest request);
    NoteResponse getNote(String id);
    List<NoteResponse> fetchNotes();
    NoteResponse updateNote(NoteRequest note, String noteId);
    NoteResponse addContributor(String noteId,String contributorEmail);
    NoteResponse removeContributor(String noteId,String contributorId);
    void deleteNote(String noteId);
}
