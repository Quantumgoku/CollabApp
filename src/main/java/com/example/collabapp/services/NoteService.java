package com.example.collabapp.services;

import com.example.collabapp.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    Note saveNote(Note note);
    List<Note> fetchNotes();
    Optional<Note> updateNote(Note note, String noteId);
    void deleteNote(String noteId);
}
