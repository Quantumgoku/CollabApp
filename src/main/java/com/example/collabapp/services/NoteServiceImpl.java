package com.example.collabapp.services;

import com.example.collabapp.model.dao.Note;
import com.example.collabapp.model.dto.request.NoteRequest;
import com.example.collabapp.model.dto.response.NoteResponse;
import com.example.collabapp.model.dto.response.NotesResponse;
import com.example.collabapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    private Note mapToNote(NoteRequest request){

        return Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    private NoteResponse mapToNoteResponse(Optional<Note> note){
        return NoteResponse.builder()
                .id(note.get().getId())
                .title(note.get().getTitle())
                .content(note.get().getContent())
                .build();
    }

    @Override
    public NoteResponse saveNote(NoteRequest request) {
        Note note = noteRepository.save(mapToNote(request));
        return mapToNoteResponse(Optional.of(note));
    }

    @Override
    public NoteResponse getNote(String id){
        Optional<Note> note=noteRepository.findById(id);
        return mapToNoteResponse(note);
    }

    @Override
    public NotesResponse fetchNotes() {
        return (NotesResponse) noteRepository.findAll()
                .stream()
                .map(note -> mapToNoteResponse(Optional.of(note)))
                .toList();
    }

    @Override
    public Optional<NoteResponse> updateNote(NoteRequest request, String noteId) {
        Optional<Note> noteStored=noteRepository.findById(noteId);
        noteStored= Optional.of(mapToNote(request));
        return Optional.ofNullable(mapToNoteResponse(noteStored));
    }

    @Override
    public void deleteNote(String noteId) {

        noteRepository.deleteById(noteId);
    }
}
