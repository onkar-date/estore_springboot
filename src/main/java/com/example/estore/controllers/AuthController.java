package com.example.estore.controllers;

import com.example.estore.dto.UserDTO;
import com.example.estore.dto.request.AddUserRequest;
import com.example.estore.dto.request.AuthenticationRequest;
import com.example.estore.dto.response.AuthenticationResponse;
import com.example.estore.entity.User;
import com.example.estore.services.UserService;
import com.example.estore.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody AddUserRequest userDto) {
        UserDTO savedUser = userService.registerUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest request) {

        // For example:
        String username = request.getUsername();
        String password = request.getPassword();

        // Validate the credentials
         User user = userService.validateUser(username, password); // Implement this method
         if (user == null) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
         }

        // Generate JWT token
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(userService.mapToAuthenticationResponse(token, user));
    }
}
