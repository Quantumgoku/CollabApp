package com.example.collabapp.services.impl;

import com.example.collabapp.exception.AccessDeniedException;
import com.example.collabapp.exception.NoteNotFoundException;
import com.example.collabapp.exception.StaleNoteException;
import com.example.collabapp.exception.UserNotFoundException;
import com.example.collabapp.model.dao.Note;
import com.example.collabapp.model.dao.NoteHistory;
import com.example.collabapp.model.dao.User;
import com.example.collabapp.model.dto.request.NoteRequest;
import com.example.collabapp.model.dto.response.NoteHistoryResponse;
import com.example.collabapp.model.dto.response.NoteResponse;
import com.example.collabapp.model.dto.response.UserResponse;
import com.example.collabapp.model.event.NoteEditEvent;
import com.example.collabapp.repository.NoteHistoryRepository;
import com.example.collabapp.repository.NoteRepository;
import com.example.collabapp.repository.UserRepository;
import com.example.collabapp.services.NoteService;
import com.example.collabapp.utils.NoteSearchRequest;
import com.example.collabapp.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final UserRepository userRepository;

    private final NoteHistoryRepository noteHistoryRepository;

    private final MongoTemplate mongoTemplate;

    private final SecurityUtils securityUtils;

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public NoteResponse saveNote(NoteRequest request) {
        String ownerId = securityUtils.getCurrentUserId();
        Note note = Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .ownerId(ownerId)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .version(1)
                .build();
        log.info("Saving the note in repo");
        return mapToNoteResponse(noteRepository.save(note));
    }

    @Override
    public NoteResponse getNote(String id){
        String userId = securityUtils.getCurrentUserId();
        Note note=getNoteWithAccess(id,userId);
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

        log.info("Incoming version: {}, DB version: {}", request.getVersion(), note.getVersion());

        if (note.getVersion() != request.getVersion()) {
            throw new StaleNoteException();
        }

        note.setContent(request.getContent());
        note.setTitle(request.getTitle());
        note.setUpdateAt(LocalDateTime.now());
        note.setVersion(note.getVersion()+1);

        Note saved = noteRepository.save(note);

        log.info("Update the note in the repo");

        noteHistoryRepository.save(NoteHistory.builder()
                .noteId(saved.getId())
                .editedBy(userId)
                .content(saved.getContent())
                .version(saved.getVersion())
                .editedAt(LocalDateTime.now())
                .build());
        log.info("Saved history for note:{} version: {}",note,saved.getVersion());

        //broadcast to WS subs
        userRepository.findById(userId).ifPresent(user -> {
            messagingTemplate.convertAndSend(
                    "/topic/note."+noteId,
                    NoteEditEvent.builder()
                            .noteId(noteId)
                            .editedBy(userId)
                            .editedByName(user.getUsername())
                            .title(saved.getTitle())
                            .content(saved.getContent())
                            .version(saved.getVersion())
                            .editedAt(LocalDateTime.now())
                            .build()
            );
        });

        return mapToNoteResponse(saved);
    }

    @Override
    public NoteResponse addContributor(String noteId, String contributorEmail) {
        String userId = securityUtils.getCurrentUserId();
        Note note = getNoteAsOwner(noteId,userId);

        User contributor = userRepository.findByEmail(contributorEmail)
                .orElseThrow(()->new UserNotFoundException("email: "+contributorEmail));
        log.info("Found user with email-> {}",contributorEmail);
        if(note.getListOfContributors()==null){
            note.setListOfContributors(new ArrayList<>());
        }
        if(!note.getListOfContributors().contains(contributor.getId())){
            note.getListOfContributors().add(contributor.getId());
        }
        log.info("Adding {} user to note {}",contributorEmail,noteId);
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
    public List<NoteHistoryResponse> getNoteHistory(String noteId) {
        String userId = securityUtils.getCurrentUserId();
        getNoteWithAccess(noteId,userId);

        return noteHistoryRepository.findByNoteIdOrderByVersionDesc(noteId)
                .stream()
                .map(h->NoteHistoryResponse.builder()
                        .id(h.getId())
                        .noteId(h.getNoteId())
                        .editedBy(h.getEditedBy())
                        .title(h.getTitle())
                        .content(h.getContent())
                        .editedAt(h.getEditedAt())
                        .version(h.getVersion())
                        .build())
                .toList();
    }

    @Override
    public List<UserResponse> getContributors(String noteId) {
        String userId = securityUtils.getCurrentUserId();
        Note note = getNoteWithAccess(noteId,userId);

        if(note.getListOfContributors()==null || note.getListOfContributors().isEmpty()){
            return List.of();
        }

        return note.getListOfContributors().stream()
                .map(contributorId -> userRepository.findById(contributorId)
                        .orElse(null))
                .filter(Objects::nonNull)
                .map(user->UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .userName(user.getUsername())
                        .build())
                .toList();
    }

    @Override
    public List<NoteResponse> sharedWithMe() {
        String userId = securityUtils.getCurrentUserId();
        return noteRepository.findByListOfContributorsContaining(userId)
                .stream()
                .map(this::mapToNoteResponse)
                .toList();
    }

    @Override
    public Page<NoteResponse> searchNotes(NoteSearchRequest request) {
        String userId = securityUtils.getCurrentUserId();

        Sort sort = request.getSortDir().equals("asc")
                ?Sort.by(request.getSortBy()).ascending()
                :Sort.by(request.getSortBy()).descending();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),sort);

        Query query = new Query();

        Criteria accessCriteria = new Criteria().orOperator(
                Criteria.where("ownerId").is(userId),
                Criteria.where("lisOfContributors").in(userId)
        );
        query.addCriteria(accessCriteria);

        if(request.getQuery() != null && !request.getQuery().isBlank()){
            query.addCriteria(Criteria.where("$text")
                    .is(new Document("$search",request.getQuery())));
        }

        long total = mongoTemplate.count(query,Note.class);
        query.with(pageable);
        List<Note> notes = mongoTemplate.find(query,Note.class);

        return new PageImpl<>(
                notes.stream()
                        .map(this::mapToNoteResponse)
                        .toList(),
                pageable,
                total
        );
    }

    @Override
    public void leaveNote(String noteId) {
        String userId = securityUtils.getCurrentUserId();
        Note note = noteRepository.findById(noteId)
                .orElseThrow(()->new NoteNotFoundException(noteId));

        if(note.getOwnerId().equals(userId)){
            throw new RuntimeException("Owner cannot leave their own note, delete the note instead");
        }

        if(note.getListOfContributors()!=null){
            note.getListOfContributors().remove(userId);
            noteRepository.save(note);
            log.info("User {} left note {}",userId,noteId);
        }
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
                .ownerId(note.getOwnerId())
                .version(note.getVersion())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdateAt())
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
