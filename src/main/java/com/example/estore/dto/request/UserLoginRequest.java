package com.example.estore.dto.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    String username;
    String password;
}
