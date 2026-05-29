package com.example.collabapp.services;

import com.example.collabapp.model.dao.User;
import com.example.collabapp.model.dto.LoginUser;
import com.example.collabapp.model.dto.RegisterUser;
import com.example.collabapp.model.dto.request.UserRequest;
import com.example.collabapp.model.dto.response.UserResponse;
import com.example.collabapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    private User mapToUser(UserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .build();
    }

    @Override
    public UserResponse addUser(UserRequest request) {
        log.info("User Service add user service");
        User user = mapToUser(request);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Override
    public UserResponse registerUser(RegisterUser user){
        if(emailExist(user.getEmail())){
            throw new RuntimeException("There is an account with that email address:" + user.getEmail());
        }
        //bcrypt stores hash and salt in same string the hashed string is separated into 4 part 3 by $, 1st is algo ver, 2nd is strength of algo 3rd 22 char salt, and rest are actual salted+hashed Password
        User savedUser = userRepository.save(User.builder()
                        .createdAt(LocalDateTime.now())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .hashedPassword(passwordEncoder.encode(user.getPassword()))
                .build());
        log.info("Registered User -> {}",savedUser);
        return mapToResponse(savedUser);
    }

    @Override
    public UserResponse loginUser(LoginUser user) {
        User savedUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + user.getEmail()));

        if (!passwordEncoder.matches(user.getPassword(), savedUser.getHashedPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return mapToResponse(savedUser);
    }

    @Override
    public UserResponse getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));
        log.info("Fetched user: {}",mapToResponse(user));
        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> fetchUsers() {
        log.info("fetch all users service");
        List<User> users = userRepository.findAll();
        log.info("Fetch list of users object -> {}",users);
        return users.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(UserRequest request, String id) {
        User existingUser=userRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Invalid ID")
        );
        existingUser.setUsername(request.getUsername());
        existingUser.setEmail(request.getEmail());
        User updatedUser = userRepository.save(existingUser);
        log.info("Updating the user in repo");
        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        log.info("Deleting the user in repo");
        userRepository.deleteById(id);
    }


    private boolean emailExist(String email){
        List<User> users = userRepository.findAll();
        for(User user:users){
            if(email.equals(user.getEmail())){
                return true;
            }
        }
        return false;
    }

    private User findUserByEmail(String email){
        List<User> users = userRepository.findAll();
        for(User user:users){
            if(email.equals(user.getEmail())){
                return user;
            }
        }
        return null;
    }
}