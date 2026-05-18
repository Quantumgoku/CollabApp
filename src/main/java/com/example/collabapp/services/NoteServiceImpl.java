package com.example.collabapp.services;

import com.example.collabapp.model.dao.Note;
import com.example.collabapp.model.dto.request.NoteRequest;
import com.example.collabapp.model.dto.response.NoteResponse;
import com.example.collabapp.repository.NoteRepository;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private static final Logger logger= LoggerFactory.getLogger(NoteServiceImpl.class);

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
        note.setCreatedAt(Long.parseLong(LocalDateTime.now().toString()));
        note.setUpdateAt(Long.parseLong(LocalDateTime.now().toString()));
        logger.info("Saving the note in repo");
        return mapToNoteResponse(note);
    }

    @Override
    public NoteResponse getNote(String id){
        Note note=noteRepository.findById(id).orElseThrow(
                ()->new RuntimeException("No note for the id")
        );
        logger.info("Getting the note from repo -> {}",note);
        return mapToNoteResponse(note);
    }

    @Override
    public List<NoteResponse> fetchNotes() {
        List<Note> noteList=noteRepository.findAll();
        logger.info("Getting the list of notes from the repo");
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
        logger.info("Update the note in the repo");
        return mapToNoteResponse(updatedNote);
    }

    @Override
    public void deleteNote(String noteId) {
        logger.info("Deleting the note from repo");
        noteRepository.deleteById(noteId);
    }
}
