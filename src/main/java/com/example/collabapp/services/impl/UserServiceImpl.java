package com.example.collabapp.services.impl;

import com.example.collabapp.exception.AccessDeniedException;
import com.example.collabapp.exception.UserNotFoundException;
import com.example.collabapp.model.dao.RefreshToken;
import com.example.collabapp.model.dao.User;
import com.example.collabapp.model.dto.LoginResponse;
import com.example.collabapp.model.dto.LoginUser;
import com.example.collabapp.model.dto.RegisterUser;
import com.example.collabapp.model.dto.request.UserRequest;
import com.example.collabapp.model.dto.response.UserResponse;
import com.example.collabapp.repository.UserRepository;
import com.example.collabapp.services.JwtService;
import com.example.collabapp.services.RefreshTokenService;
import com.example.collabapp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;

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
    public UserResponse registerUser(RegisterUser user) {
        if (emailExist(user.getEmail())) {
            throw new RuntimeException("There is an account with that email address:" + user.getEmail());
        }
        //bcrypt stores hash and salt in same string the hashed string is separated into 4 part 3 by $, 1st is algo ver, 2nd is strength of algo 3rd 22 char salt, and rest are actual salted+hashed Password
        User savedUser = userRepository.save(User.builder()
                .createdAt(LocalDateTime.now())
                .email(user.getEmail())
                .username(user.getUsername())
                .hashedPassword(passwordEncoder.encode(user.getPassword()))
                .build());
        log.info("Registered User -> {}", savedUser);
        return mapToResponse(savedUser);
    }

    @Override
    public LoginResponse loginUser(LoginUser user) {
        User savedUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("email: "+user.getEmail()));

        if (!passwordEncoder.matches(user.getPassword(), savedUser.getHashedPassword())) {
            throw new AccessDeniedException("Invalid password");
        }
        String accessToken = jwtService.generateAccessToken(savedUser.getId(), savedUser.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser.getId(), user.getDeviceInfo());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .user(mapToResponse(savedUser))
                .build();
    }

    @Override
    public LoginResponse refresh(Map<String, String> body) {
        String oldToken = body.get("refreshToken");
        if (oldToken == null) throw new RuntimeException("Refresh token is required");

        RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(oldToken, refreshTokenService.getUserIdFromToken(oldToken));
        User user = userRepository.findById(newRefreshToken.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail());
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getToken())
                .user(UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .userName(user.getUsername())
                        .build())
                .build();
    }

    @Override
    public void logout(Map<String,String> body){
        String refreshToken = body.get("refreshToken");
        if(refreshToken!=null){
            refreshTokenService.revokeToken(refreshToken);
        }
    }

    @Override
    public UserResponse getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("id: "+id));
        log.info("Fetched user: {}", mapToResponse(user));
        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> fetchUsers() {
        log.info("fetch all users service");
        List<User> users = userRepository.findAll();
        log.info("Fetch list of users object -> {}", users);
        return users.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(UserRequest request, String id) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("id: "+id)
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
            throw new UserNotFoundException("id: "+id);
        }
        log.info("Deleting the user in repo");
        userRepository.deleteById(id);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}