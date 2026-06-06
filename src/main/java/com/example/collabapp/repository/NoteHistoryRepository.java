package com.example.collabapp.repository;

import com.example.collabapp.model.dao.NoteHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteHistoryRepository extends MongoRepository<NoteHistory,String> {
    List<NoteHistory> findByNoteIdOrderByVersionDesc(String noteId);
}
