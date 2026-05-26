package com.example.collabapp.services;

import com.example.collabapp.model.dao.User;
import com.example.collabapp.model.dto.request.UserRequest;
import com.example.collabapp.model.dto.response.UserResponse;
import com.example.collabapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private ObjectMapper objectMapper;

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
        user.setCreatedAt(Long.parseLong(LocalDateTime.now().toString()));
        User savedUser = userRepository.save(user);
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
}
