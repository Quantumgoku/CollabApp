package com.example.collabapp.services;

import com.example.collabapp.model.dao.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUser(User user);
    User getUser(String id);
    List<User> fetchUsers();
    Optional<User> updateUser(User user, String id);
    void deleteUser(String id);
}
