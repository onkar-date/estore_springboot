package com.example.estore.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private boolean active = true; // Default value
}
