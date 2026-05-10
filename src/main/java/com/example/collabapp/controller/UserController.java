package com.example.collabapp.controller;

import com.example.collabapp.model.dao.User;
import com.example.collabapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable String id){
        return userService.getUser(id);
    }

    @GetMapping("/users")
    public List<User> fetchAllUsers(){
        return userService.fetchUsers();
    }

    @PutMapping("/user/{id}")
    public Optional<User> updateUser(@RequestBody User user,@PathVariable String id){
        return userService.updateUser(user,id);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable String id){
        userService.deleteUser(id);
    }
}