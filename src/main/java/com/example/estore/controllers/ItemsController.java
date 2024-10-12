package com.example.estore.controllers;


import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
public class ItemsController {

    @GetMapping("test")
    public ResponseEntity<String> testAPI() {
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
