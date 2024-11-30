package com.example.estore.security;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class KeyGenerator {

    public static void main(String[] args) {
        // Generate a secure key for HMAC-SHA256
        SecretKey secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

        // Print the key in Base64 encoding
        System.out.println("Generated Key: " + java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded()));
    }
}
