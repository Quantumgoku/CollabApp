package com.example.collabapp.controller;

import com.example.collabapp.model.dto.LoginResponse;
import com.example.collabapp.model.dto.LoginUser;
import com.example.collabapp.model.dto.RegisterUser;
import com.example.collabapp.model.dto.request.UserRequest;
import com.example.collabapp.model.dto.response.UserResponse;
import com.example.collabapp.services.JwtService;
import com.example.collabapp.services.RefreshTokenService;
import com.example.collabapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest user){
        log.info("Handling user post mapping");
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterUser user){
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginUser user, HttpServletRequest request){
        user.setDeviceInfo(request.getHeader("User-Agent"));
        return ResponseEntity.ok(userService.loginUser(user));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody Map<String,String> body){
        return ResponseEntity.ok(userService.refresh(body));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@RequestBody Map<String,String> body){
        userService.logout(body);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){
        log.info("Handling user get mapping");
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> fetchAllUsers(){
        log.info("Handling users get mapping");
        return ResponseEntity.ok(userService.fetchUsers());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest,@PathVariable String id){
        log.info("Handling user update mapping");
        return ResponseEntity.ok(userService.updateUser(userRequest,id));
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable String id){
        log.info("Handling user delete mapping");
        userService.deleteUser(id);
    }
}