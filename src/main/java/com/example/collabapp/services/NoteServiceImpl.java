package com.example.collabapp.services;

import com.example.collabapp.exception.AccessDeniedException;
import com.example.collabapp.exception.NoteNotFoundException;
import com.example.collabapp.exception.UserNotFoundException;
import com.example.collabapp.model.dao.Note;
import com.example.collabapp.model.dao.User;
import com.example.collabapp.model.dto.request.NoteRequest;
import com.example.collabapp.model.dto.response.NoteResponse;
import com.example.collabapp.repository.NoteRepository;
import com.example.collabapp.repository.UserRepository;
import com.example.collabapp.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public NoteResponse saveNote(NoteRequest request) {
        String ownerId = securityUtils.getCurrentUserId();
        Note note = Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .ownerId(ownerId)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        log.info("Saving the note in repo");
        return mapToNoteResponse(noteRepository.save(note));
    }

    @Override
    public NoteResponse getNote(String id){
        Note note=noteRepository.findById(id).orElseThrow(
                ()->new NoteNotFoundException(id)
        );
        log.info("Getting the note from repo -> {}",note);
        return mapToNoteResponse(note);
    }

    @Override
    public List<NoteResponse> fetchNotes() {
        String userId = securityUtils.getCurrentUserId();
        log.info("Getting the list of notes from the repo");
        return noteRepository.findByOwnerIdOrListOfContributorsContaining(userId, userId)
                .stream().map(this::mapToNoteResponse).toList();
    }

    @Override
    public NoteResponse updateNote(NoteRequest request, String noteId) {
        String userId = securityUtils.getCurrentUserId();
        Note note = getNoteWithAccess(noteId, userId);
        note.setContent(request.getContent());
        note.setTitle(request.getTitle());
        note.setUpdateAt(LocalDateTime.now());
        log.info("Update the note in the repo");
        return mapToNoteResponse(noteRepository.save(note));
    }

    @Override
    public NoteResponse addContributor(String noteId, String contributorEmail) {
        String userId = securityUtils.getCurrentUserId();
        Note note = getNoteAsOwner(noteId,userId);

        User contributor = userRepository.findByEmail(contributorEmail)
                .orElseThrow(()->new UserNotFoundException("email: "+contributorEmail));
        if(note.getListOfContributors()==null){
            note.setListOfContributors(new ArrayList<>());
        }
        if(!note.getListOfContributors().contains(contributor.getId())){
            note.getListOfContributors().add(contributor.getId());
        }
        return mapToNoteResponse(noteRepository.save(note));
    }

    @Override
    public NoteResponse removeContributor(String noteId, String contributorId) {
        String userId = securityUtils.getCurrentUserId();
        Note note = getNoteAsOwner(noteId,userId);

        if(note.getListOfContributors() != null){
            note.getListOfContributors().remove(contributorId);
        }
        return mapToNoteResponse(noteRepository.save(note));
    }

    @Override
    public void deleteNote(String noteId) {
        String userId = securityUtils.getCurrentUserId();
        Note note = noteRepository.findById(noteId)
                        .orElseThrow(()->new NoteNotFoundException(noteId));
        if(!note.getOwnerId().equals(userId)){
            throw new AccessDeniedException("Only the owner can delete this note");
        }
        log.info("Deleting the note from repo");
        noteRepository.deleteById(noteId);
    }

    //Helpers
    private NoteResponse mapToNoteResponse(Note note){
        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }

    private Note getNoteWithAccess(String noteId, String userId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(noteId));

        boolean isOwner = note.getOwnerId().equals(userId);
        boolean isContributor = note.getListOfContributors() != null &&
                note.getListOfContributors().contains(userId);

        if (!isOwner && !isContributor) {
            throw new AccessDeniedException("Access denied");
        }
        return note;
    }

    private Note getNoteAsOwner(String noteId, String userId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(()->new NoteNotFoundException(noteId));
        if(!note.getOwnerId().equals(userId)){
            throw new AccessDeniedException("Only the owner can perform this action");
        }
        return note;
    }
}
