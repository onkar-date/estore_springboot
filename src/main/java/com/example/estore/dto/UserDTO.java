package com.example.estore.dto;

import com.example.estore.enums.UserType;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserType role;
    private boolean active;
}
