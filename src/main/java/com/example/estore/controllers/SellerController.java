package com.example.estore.controllers;

import com.example.estore.dto.UserDto;
import com.example.estore.entity.User;
import com.example.estore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addSeller(@RequestBody UserDto userDto) {
        User savedUser = userService.addSeller(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}

