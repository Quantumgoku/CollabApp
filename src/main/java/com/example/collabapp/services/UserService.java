package com.example.collabapp.services;

import com.example.collabapp.model.dto.LoginResponse;
import com.example.collabapp.model.dto.LoginUser;
import com.example.collabapp.model.dto.RegisterUser;
import com.example.collabapp.model.dto.request.UserRequest;
import com.example.collabapp.model.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
        UserResponse addUser(UserRequest request);
        UserResponse getUser(String id);
        List<UserResponse> fetchUsers();
        UserResponse updateUser(UserRequest request, String id);
        void deleteUser(String id);

        UserResponse registerUser(RegisterUser user);
        LoginResponse loginUser(LoginUser user);

        LoginResponse refresh(Map<String, String> body);
        void logout(Map<String,String> body);
}
