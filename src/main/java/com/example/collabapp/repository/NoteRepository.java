package com.example.collabapp.repository;

import com.example.collabapp.model.dao.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String> {
}
