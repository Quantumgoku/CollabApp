package com.example.collabapp.controller;

import com.example.collabapp.model.dto.request.UserRequest;
import com.example.collabapp.model.dto.response.UserResponse;
import com.example.collabapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest user){
        logger.info("Handling user post mapping");
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){
        logger.info("Handling user get mapping");
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> fetchAllUsers(){
        logger.info("Handling users get mapping");
        return ResponseEntity.ok(userService.fetchUsers());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest,@PathVariable String id){
        logger.info("Handling user update mapping");
        return ResponseEntity.ok(userService.updateUser(userRequest,id));
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable String id){
        logger.info("Handling user delete mapping");
        userService.deleteUser(id);
    }
}