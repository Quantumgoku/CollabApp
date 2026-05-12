package com.example.collabapp.services;

import com.example.collabapp.model.dao.User;
import com.example.collabapp.model.dto.request.UserRequest;
import com.example.collabapp.model.dto.response.UserResponse;
import com.example.collabapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .build();
    }

    private User mapToUser(UserRequest request) {
        return User.builder()
                .username(request.getUserName())
                .build();
    }

    @Override
    public UserResponse addUser(UserRequest request) {
        User user = mapToUser(request);
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Override
    public UserResponse getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));

        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> fetchUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(UserRequest request, String id) {
        User existingUser=userRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Invalid ID")
        );
        existingUser.setUsername(request.getUserName());
        existingUser.setEmail(request.getEmail());
        User updatedUser = userRepository.save(existingUser);

        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteUser(String id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }
}
