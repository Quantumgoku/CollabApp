package com.example.collabapp.services;

import com.example.collabapp.model.dto.request.NoteRequest;
import com.example.collabapp.model.dto.response.NoteHistoryResponse;
import com.example.collabapp.model.dto.response.NoteResponse;
import com.example.collabapp.model.dto.response.UserResponse;
import com.example.collabapp.utils.NoteSearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoteService {
    NoteResponse saveNote(NoteRequest request);
    NoteResponse getNote(String id);
    List<NoteResponse> fetchNotes();
    NoteResponse updateNote(NoteRequest note, String noteId);
    NoteResponse addContributor(String noteId,String contributorEmail);
    NoteResponse removeContributor(String noteId,String contributorId);
    List<NoteHistoryResponse> getNoteHistory(String noteId);
    List<UserResponse> getContributors(String noteId);
    List<NoteResponse> sharedWithMe();
    Page<NoteResponse> searchNotes(NoteSearchRequest request);
    void leaveNote(String noteId);
    void deleteNote(String noteId);
}
