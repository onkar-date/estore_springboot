package com.example.estore.controllers;

import com.example.estore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private UserService userService;

}

