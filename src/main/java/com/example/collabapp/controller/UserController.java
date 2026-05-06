package com.example.collabapp.controller;

import com.example.collabapp.model.User;
import com.example.collabapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping
    public User getUser(@PathVariable String id){
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> fetchAllUsers(){
        return userService.fetchUsers();
    }

    @PutMapping
    public Optional<User> updateUser(@RequestBody User user,@PathVariable String id){
        return userService.updateUser(user,id);
    }

    @DeleteMapping
    public void deleteUser(@PathVariable String id){
        userService.deleteUser(id);
    }

}
