package com.example.collabapp.services;

import com.example.collabapp.model.Note;
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

    @Override
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note getNote(String id){
        Optional<Note> note;
        if(id != null){
            note = noteRepository.findById(id);
        }else{
            throw new RuntimeException("Invalid ID");
        }
        return note.get();
    }

    @Override
    public List<Note> fetchNotes() {
        List<Note> listOfNotes=new ArrayList<>();
        listOfNotes = noteRepository.findAll();
        return listOfNotes;
    }

    @Override
    public Optional<Note> updateNote(Note note, String noteId) {
        Optional<Note> noteStored=noteRepository.findById(noteId);
        noteStored= Optional.ofNullable(note);
        return noteStored;
    }

    @Override
    public void deleteNote(String noteId) {

        noteRepository.deleteById(noteId);
    }
}
