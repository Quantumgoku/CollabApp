package com.example.collabapp.services;

import com.example.collabapp.model.dao.Note;
import com.example.collabapp.model.dto.request.NoteRequest;
import com.example.collabapp.model.dto.response.NoteResponse;
import com.example.collabapp.repository.NoteRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    private Note mapToNote(@NonNull NoteRequest request){

        return Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    private NoteResponse mapToNoteResponse(Note note){
        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }

    @Override
    public NoteResponse saveNote(NoteRequest request) {
        Note note = noteRepository.save(mapToNote(request));
        return mapToNoteResponse(note);
    }

    @Override
    public NoteResponse getNote(String id){
        Note note=noteRepository.findById(id).orElseThrow(
                ()->new RuntimeException("No note for the id")
        );
        return mapToNoteResponse(note);
    }

    @Override
    public List<NoteResponse> fetchNotes() {
        List<Note> noteList=noteRepository.findAll();
        return noteList.stream()
                .map(this::mapToNoteResponse)
                .toList();
    }

    @Override
    public NoteResponse updateNote(NoteRequest request, String noteId) {
        Note existingNote=noteRepository.findById(noteId).orElseThrow(
                ()->new RuntimeException("Invalid ID")
        );
        existingNote.setContent(request.getContent());
        existingNote.setTitle(request.getTitle());
        existingNote.setUpdateAt(Long.parseLong(LocalDateTime.now().toString()));
        Note updatedNote=noteRepository.save(existingNote);
        return mapToNoteResponse(updatedNote);
    }

    @Override
    public void deleteNote(String noteId) {

        noteRepository.deleteById(noteId);
    }
}
