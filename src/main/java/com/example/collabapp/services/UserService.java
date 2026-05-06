package com.example.collabapp.services;

import com.example.collabapp.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User getUser(String id);
    List<User> fetchUsers();
    User updateUser(User user,String id);
    void deleteUser(String id);
}
