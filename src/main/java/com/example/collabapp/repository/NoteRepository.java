package com.example.collabapp.repository;

import com.example.collabapp.model.dao.Note;
import com.example.collabapp.model.dto.response.NoteResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByOwnerIdOrListOfContributorsContaining(String ownerId,String contributorId);
    List<Note> findByListOfContributorsContaining(String userId);
}
