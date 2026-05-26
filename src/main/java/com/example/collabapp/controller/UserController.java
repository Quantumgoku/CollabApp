package com.example.collabapp.controller;

import com.example.collabapp.model.dto.request.UserRequest;
import com.example.collabapp.model.dto.response.UserResponse;
import com.example.collabapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest user){
        log.info("Handling user post mapping");
        return ResponseEntity.ok(userService.addUser(user));
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