package com.example.collabapp.controller;

import com.example.collabapp.model.dto.request.NoteRequest;
import com.example.collabapp.model.dto.response.NoteHistoryResponse;
import com.example.collabapp.model.dto.response.NoteResponse;
import com.example.collabapp.model.dto.response.UserResponse;
import com.example.collabapp.services.NoteService;
import com.example.collabapp.utils.NoteSearchRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class NoteController {

    @Autowired
    private NoteService noteService;

    //Notes

    @PostMapping("/note")
    public ResponseEntity<NoteResponse> saveNote(@Valid @RequestBody NoteRequest request){    //used ResponseEntity for granular control of HTTP response and request,
        log.info("Hitting post mapping route -> /note");
        return ResponseEntity.ok(noteService.saveNote(request));
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<NoteResponse> getNote(@PathVariable String id){
        log.info("Hitting get mapping for 1 notes route -> /note/"+id);
        return ResponseEntity.ok(noteService.getNote(id));
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteResponse>> fetchNotesList(){ //all notes associated with the user
        log.info("Hitting get mapping for notes route -> /notes");
        return ResponseEntity.ok(noteService.fetchNotes());
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<NoteResponse> updateNote(@Valid @RequestBody NoteRequest request, @PathVariable String id){
        log.info("Hitting put mapping route -> /note/{id}");
        return ResponseEntity.ok(noteService.updateNote(request,id));
    }

    @DeleteMapping("/note/{id}")
    public void deleteNote(@PathVariable String id){
        log.info("Hitting delete mapping route -> /note");
        noteService.deleteNote(id);
    }

    //Contributors

    @GetMapping("/notes/{id}/contributors")
    public ResponseEntity<List<UserResponse>> getContributors(@PathVariable String id){
        return ResponseEntity.ok(noteService.getContributors(id));
    }

    @GetMapping("/notes/shared-with-me")
    public ResponseEntity<List<NoteResponse>> getSharedWithMe(){
        return ResponseEntity.ok(noteService.sharedWithMe());
    }

    @DeleteMapping("/notes/{id}/leave")
    public ResponseEntity<Void> leaveNote(@PathVariable String id){
        noteService.leaveNote(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/notes/{id}/contributors")
    public ResponseEntity<NoteResponse> addContributor(@PathVariable String id, @RequestBody Map<String,String> body){
        return ResponseEntity.ok(noteService.addContributor(id,body.get("email")));
    }

    @DeleteMapping("/notes/{id}/contributors/{contributorId}")
    public ResponseEntity<NoteResponse> removeContributor(@PathVariable String id,@PathVariable String contributorId){
        return ResponseEntity.ok(noteService.removeContributor(id,contributorId));
    }
    //History

    @GetMapping("/notes/{id}/history")
    public ResponseEntity<List<NoteHistoryResponse>> getNoteHistory(@PathVariable String id){
        return ResponseEntity.ok(noteService.getNoteHistory(id));
    }

    //Search
    @GetMapping("/notes/search")
    public ResponseEntity<Page<NoteResponse>> searchNotes(@Valid @ModelAttribute NoteSearchRequest request){
        return ResponseEntity.ok(noteService.searchNotes(request));
    }
}
