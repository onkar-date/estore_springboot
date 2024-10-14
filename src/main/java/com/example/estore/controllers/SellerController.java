package com.example.estore.controllers;

import com.example.estore.dto.AddUserRequest;
import com.example.estore.dto.UserDTO;
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

}

