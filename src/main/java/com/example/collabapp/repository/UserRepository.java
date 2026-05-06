package com.example.collabapp.repository;

import com.example.collabapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
