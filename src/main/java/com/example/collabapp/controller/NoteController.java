package com.example.collabapp.controller;

import com.example.collabapp.model.Note;
import com.example.collabapp.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class NoteController {

    @Autowired
    NoteService noteService;

    @PostMapping("/notes")
    public Note saveNote(@RequestBody Note note){
        return noteService.saveNote(note);
    }

    @GetMapping("/notes")
    public List<Note> fetchNotesList(){
        return noteService.fetchNotes();
    }

    @PutMapping("/notes/{id}")
    public Optional<Note> updateNote(@RequestBody Note note, @PathVariable String id){
        return noteService.updateNote(note,id);
    }

    @DeleteMapping("/note/{id}")
    public void deleteNote(@PathVariable String id){
        noteService.deleteNote(id);
    }
}
