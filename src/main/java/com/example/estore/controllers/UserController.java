package com.example.estore.controllers;

import com.example.estore.dto.request.AddUserRequest;
import com.example.estore.dto.UserDTO;
import com.example.estore.dto.request.UserLoginRequest;
import com.example.estore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // Base URL for user-related APIs
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // Return 200 OK with the list of users
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> addUser(@RequestBody AddUserRequest userDto) {
        UserDTO savedUser = userService.registerUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginCredentials) {
        UserDTO userDTO = userService.login(loginCredentials.getUsername(), loginCredentials.getPassword());

        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }

        // If login fails, return a 401 Unauthorized status
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
