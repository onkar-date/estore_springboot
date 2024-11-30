package com.example.estore.dto.response;

import com.example.estore.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserType role;
    private boolean active;
}
