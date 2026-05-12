package com.example.collabapp.controller;

import com.example.collabapp.model.dto.request.NoteRequest;
import com.example.collabapp.model.dto.response.NoteResponse;
import com.example.collabapp.services.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/note")
    public ResponseEntity<NoteResponse> saveNote(@Valid @RequestBody NoteRequest request){    //used ResponseEntity for granular control of HTTP response and request,
        return ResponseEntity.ok(noteService.saveNote(request));
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<NoteResponse> getNote(@PathVariable String id){
        return ResponseEntity.ok(noteService.getNote(id));
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteResponse>> fetchNotesList(){
        return ResponseEntity.ok(noteService.fetchNotes());
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<NoteResponse> updateNote(@Valid @RequestBody NoteRequest request, @PathVariable String id){
        return ResponseEntity.ok(noteService.updateNote(request,id));
    }

    @DeleteMapping("/note/{id}")
    public void deleteNote(@PathVariable String id){
        noteService.deleteNote(id);
    }
}
