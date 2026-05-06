package com.example.collabapp.services;

import com.example.collabapp.model.User;
import com.example.collabapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(String id) {
        Optional<User> user;
        if(id != null){
            user = userRepository.findById(id);
        }else{
            throw new RuntimeException("Invalid ID");
        }
        return user.get();
    }

    @Override
    public List<User> fetchUsers() {
        List<User> listOfUsers= new  ArrayList<>();
        listOfUsers=userRepository.findAll();
        return listOfUsers;
    }

    @Override
    public Optional<User> updateUser(User user, String id) {
        Optional<User> userOptional=userRepository.findById(id);
        userOptional= Optional.ofNullable(user);
        return userOptional;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
